package com.zcy.aspect;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zcy.constant.ClientCommon;
import com.zcy.constant.RequestStatus;
import com.zcy.dao.PlatformKeyMapper;
import com.zcy.exception.SignException;
import com.zcy.model.PayLog;
import com.zcy.model.PlatformKey;
import com.zcy.service.PayLogService;
import com.zcy.utils.ClientResponseUtils;
import com.zcy.utils.HttpHelper;
import com.zcy.utils.IpUtils;
import com.zcy.utils.SignUtils;
import com.zcy.vo.BaseRequset;
import com.zcy.vo.BaseResponse;
import com.zcy.vo.BaseSign;

/**
 * 接口参数签名类 统一签名
 */
@Component
@Aspect
public class SignAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(SignAspect.class);

	/**
	 * 开始时间
	 */
	private long startTime = 0L;
	/**
	 * 结束时间
	 */
	private long endTime = 0L;

	@Autowired
	private PayLogService payLogService;

	@Autowired
	private PlatformKeyMapper platformKeyMapper;

	@Pointcut("execution(* com.zcy.controller.*.*(..)) && !@annotation(com.zcy.annotation.NoNeedCheck)")
	public void before() {
	}

	@Pointcut("execution(* com.zcy.controller.*.*(..))")
	public void around() {
	}

	@Before("before()")
	public void doBeforeInServiceLayer(JoinPoint joinPoint) {
		LOGGER.debug("doBeforeInServiceLayer");
		startTime = System.currentTimeMillis();
	}

	@After("before()")
	public void doAfterInServiceLayer(JoinPoint joinPoint) {
		LOGGER.debug("doAfterInServiceLayer");
	}

	@Before("before()")
	public void requestLimit(JoinPoint joinPoint) throws SignException {
		BaseResponse response = new BaseResponse();
		try {
			// 获取HttpRequest
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			// 检查请求方法
			String method = request.getMethod();
			// 对外API只支持POST调用
			if (!RequestMethod.POST.toString().equals(method)) {
				ClientResponseUtils.exception(response, RequestStatus.METHOD_ALLOWED.getMessage(),
						RequestStatus.METHOD_ALLOWED.getStatus());
				throw new SignException(response);
			}
			// 获取请求入参
			Object[] args = joinPoint.getArgs();
			if (args == null || args.length != 2) {
				ClientResponseUtils.exception(response, RequestStatus.PARAM_REQUIRED.getMessage(),
						RequestStatus.PARAM_REQUIRED.getStatus());
				throw new SignException(response);
			}
			Object requestData = args[1];
			BaseRequset baseRequset = (BaseRequset) requestData;
			// 请求参数不完整或未签名
			if (baseRequset == null || baseRequset.getBaseSign() == null
					|| StringUtils.isEmpty(baseRequset.getBaseSign().getApiKey())) {
				ClientResponseUtils.exception(response, RequestStatus.PARAM_REQUIRED.getMessage(),
						RequestStatus.PARAM_REQUIRED.getStatus());
				throw new SignException(response);
			}
			// 判断如果是本项目前端调用携带的api key 就无需进行sign签名验证
			BaseSign baseSign = baseRequset.getBaseSign();
			String apiKey = baseSign.getApiKey();
			// 根据第三方api key查询私钥
			PlatformKey platformKey = platformKeyMapper.selectSecretKeyByApiKey(apiKey);
			if (platformKey == null || StringUtils.isEmpty(platformKey.getSecretKey())) {
				ClientResponseUtils.exception(response, RequestStatus.PRIVATE_KEY_ABSENCE.getMessage(),
						RequestStatus.PRIVATE_KEY_ABSENCE.getStatus());
				throw new SignException(response);
			}
			// 非本项目前台api key接下来进行参数签名验证
			Object data = baseRequset.getData();
			if (data != null && apiKey != null && StringUtils.isNotEmpty(baseSign.getSign())
					&& StringUtils.isNotEmpty(baseSign.getNonceStr()) && baseSign.getTimestamp() != null) {
				Map<String, String> stringObjectMap = new HashMap<>();
				stringObjectMap.put(ClientCommon.DATA_KEY, JSON.toJSONString(data));
				stringObjectMap.put(ClientCommon.API_KEY, apiKey);
				stringObjectMap.put(ClientCommon.NONCE_STR, baseSign.getNonceStr());
				stringObjectMap.put(ClientCommon.TIME_STAMP, baseSign.getTimestamp() + "");
				stringObjectMap.put(ClientCommon.SIGN, baseSign.getSign());
				// 私钥
				String secretKey = platformKey.getSecretKey();
				boolean isLegal = SignUtils.checkSign(stringObjectMap, platformKey.getSecretKey());
				request.setAttribute(ClientCommon.SECRET_KEY, secretKey);
				if (!isLegal) {
					ClientResponseUtils.exception(response, RequestStatus.SIGN_INVALID.getMessage(),
							RequestStatus.SIGN_INVALID.getStatus());
					throw new SignException(response);
				}
			} else {
				ClientResponseUtils.exception(response, RequestStatus.PARAM_REQUIRED.getMessage(),
						RequestStatus.PARAM_REQUIRED.getStatus());
				throw new SignException(response);
			}
		} catch (SignException e) {
			LOGGER.error("签名鉴权失败 e " + e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("签名鉴权失败 e " + e);
			ClientResponseUtils.exception(response, RequestStatus.FAILED.getMessage(),
					RequestStatus.FAILED.getStatus());
			throw new SignException(response);
		}
	}

	@Around("around()")
	public Object recordSysLog(ProceedingJoinPoint joinPoint) throws Throwable {
		String userId = "0";
		// 请求的方法名
		String strMethodName = joinPoint.getSignature().getName();
		// 请求的类名
		String strClassName = joinPoint.getTarget().getClass().getSimpleName();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		StringBuffer bfParams = new StringBuffer();
		String param = HttpHelper.getBodyString(request);
		if (StringUtils.isBlank(param)) {
			Enumeration<String> paraNames = request.getParameterNames();
			String key;
			String value;
			while (paraNames.hasMoreElements()) {
				// 遍历请求参数
				key = paraNames.nextElement();
				value = request.getParameter(key);
				bfParams.append(key).append("=").append(value).append("&");
			}
			if (StringUtils.isBlank(bfParams)) {
				// 如果请求参数为空,返回url路径后面的查询字符串
				bfParams.append(request.getQueryString());
			}
		} else {
			bfParams.append(param);
		}

		String strMessage = String.format("[类名]:%s,[方法]:%s,[参数]:%s", strClassName, strMethodName, bfParams);
		LOGGER.info(strMessage);

		// 环绕通知 ProceedingJoinPoint执行proceed方法的作用是让目标方法执行，这也是环绕通知和前置、后置通知方法的一个最大区别。
		Object result = joinPoint.proceed();
		endTime = System.currentTimeMillis();
		LOGGER.debug("doAround>>>result={},耗时：{}", result, endTime - startTime);

		PayLog sysLog = new PayLog();
		sysLog.setCreateTime(new Date());
		sysLog.setUserId(userId);
		sysLog.setOptContent(strMessage);
		sysLog.setUserIp(IpUtils.getIp(request));
		sysLog.setUrl(request.getRequestURI());
		sysLog.setMethod(request.getMethod());
		sysLog.setUserAgent(request.getHeader("User-Agent"));
		sysLog.setSpendTime((int) (endTime - startTime));
		if (result != null) {
			sysLog.setOptResult(JSONObject.toJSON(result).toString());
		}
		payLogService.saveLog(sysLog);
		return result;
	}

	public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}
}