package com.zcy.aspect;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zcy.annotation.LimitRequest;
import com.zcy.dao.WhiteIpMapper;
import com.zcy.exception.LimitIPRequestException;
import com.zcy.exception.WhiteIpException;
import com.zcy.jedis.JedisClient;
import com.zcy.model.WhiteIp;
import com.zcy.utils.IpUtils;

import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 接口限流切面
 */
@Component
@Aspect
public class LimitRequestAspect {

	private static final Logger log = LoggerFactory.getLogger(LimitRequestAspect.class);

	@Autowired
	private JedisClient jedis;

	@Autowired
	private WhiteIpMapper whiteIpMapper;

	@Pointcut("execution(* com.zcy.controller.*.*(..)) && @annotation(com.zcy.annotation.LimitRequest)")
	public void before() {
	}

	@Before("before()")
	public void requestLimit(JoinPoint joinPoint) throws LimitIPRequestException, WhiteIpException {
		try {
			// 获取HttpRequest
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();

			// 判断request不能为空
			if (request == null) {
				throw new LimitIPRequestException("HttpServletRequest有误...");
			}
			LimitRequest limit = this.getAnnotation(joinPoint);
			if (limit == null) {
				return;
			}

			String ip = IpUtils.getIp(request);
			String uri = request.getRequestURI();
			// 校验ip是否在白名单中
			Boolean flag = true;
			List<WhiteIp> whiteIpList = whiteIpMapper.selectWhiteIpList();
			if (!CollectionUtils.isEmpty(whiteIpList)) {
				for (WhiteIp whiteIp : whiteIpList) {
					if (ip.equals(whiteIp.getIpAddress())) {
						flag = false;
						break;
					}
				}
			}
			// ip不在白名单中
			if (flag) {
				log.info("非白名单IP[" + ip + "]访问地址[" + uri + "]");
				throw new WhiteIpException();
			}

			String redisKey = "limit-ip-userId-request:" + uri + ":" + ip;
			// 设置在redis中的缓存，累加1
			long count = jedis.incr(redisKey);

			// 如果该key不存在，则从0开始计算，并且当count为1的时候，设置过期时间
			if (count == 1) {
				jedis.expire(redisKey, limit.timeSecond());
			}

			// 如果redis中的count大于限制的次数，则报错
			if (count > limit.limitCounts()) {
				log.info("用户IP[" + ip + "]访问地址[" + uri + "]超过了限定的次数[" + limit.limitCounts() + "]");
				throw new LimitIPRequestException();
			}
		} catch (WhiteIpException e) {
			throw e;
		} catch (LimitIPRequestException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof JedisConnectionException) {
				log.error("redis 运行异常 e : " + e);
			}
			log.error("requestLimit e : " + e);
			// throw new LimitIPRequestException();
		}
	}

	/**
	 * 
	 * @Description: 获得注解
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	private LimitRequest getAnnotation(JoinPoint joinPoint) throws Exception {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		if (method != null) {
			return method.getAnnotation(LimitRequest.class);
		}
		return null;
	}
}