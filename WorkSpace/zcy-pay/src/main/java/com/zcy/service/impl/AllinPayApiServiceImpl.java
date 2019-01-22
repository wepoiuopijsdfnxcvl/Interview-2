package com.zcy.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zcy.service.AllinPayApiService;
import com.zcy.utils.AllinSignUtils;
import com.zcy.utils.HttpClientHelper;
import com.zcy.vo.CardAddResponse;
import com.zcy.vo.CardBackResponse;
import com.zcy.vo.CardRegisterResponse;

public class AllinPayApiServiceImpl implements AllinPayApiService {

	@Value("${app_key}")
	private String app_key; // 通联分配给应用的AppKey
	@Value("${app_secret}")
	private String app_secret; // 签名密钥
	@Value("${mer_id}")
	private String mer_id;// 商户号
	@Value("${brh_id}")
	private String brh_id;// 请求密钥
	@Value("${req_secret}")
	private String req_secret;// 数据密钥
	@Value("${data_secret}")
	private String data_secret;// 版本号，目前默认值：1.0
	@Value("${v}")
	private String v;// 版本号，目前默认值：1.0
	@Value("${sign_v}")
	private String sign_v;// 签名版本号，目前默认值：1
	@Value("${format}")
	private String format;// 响应数据格式
	@Value("${prdt_no}")
	private String prdt_no;// 产品编号
	@Value("${top_up_way}")
	private String top_up_way;// 充值方式，默认值：1
	@Value("${opr_id}")
	private String opr_id;// 操作人
	@Value("${crosscustregister}")
	private String crosscustregister; // 客户注册（虚拟卡）
	@Value("${cardsingletopup}")
	private String cardsingletopup; // 充值
	@Value("${cardback}")
	private String cardback;// 提供订单号、原交易订单号、商户号进行直接退货

	private static final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Logger log = LoggerFactory.getLogger(AllinPayApiServiceImpl.class);

	private String bulidSignAndGenUrl(TreeMap<String, String> queryMap, String method) {
		String url = null;
		// 封装签名参数
		queryMap.put("app_key", app_key);
		queryMap.put("v", v);
		queryMap.put("sign_v", sign_v);
		queryMap.put("format", format);
		queryMap.put("timestamp", df.format(new Date()));
		queryMap.put("method", method);

		try {
			// 添加签名
			TreeMap<String, String> map = AllinSignUtils.addSign(queryMap, app_secret);
			// 生成调用url
			url = AllinSignUtils.genUrl(map);
		} catch (Exception e) {
			log.error("bulidSignMap fail:" + e.getMessage());
		}
		return url;
	}

	@Override
	public CardRegisterResponse cardRegister(TreeMap<String, String> queryMap) {
		CardRegisterResponse response = new CardRegisterResponse();
		try {
			String url = bulidSignAndGenUrl(queryMap, crosscustregister);
			String result = HttpClientHelper.doGet(url);
			if (StringUtils.isBlank(result)) {
			}
			response = JSONObject.parseObject(JSON.toJSONString(result), response.getClass());
			if (response.getCode() != null || response.getMsg() != null) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public CardAddResponse cardAdd(TreeMap<String, String> queryMap) {
		CardAddResponse response = new CardAddResponse();
		bulidSignAndGenUrl(queryMap, cardsingletopup);
		return response;
	}

	@Override
	public CardBackResponse cardBack(TreeMap<String, String> queryMap) {
		CardBackResponse response = new CardBackResponse();
		queryMap.put("req_secret", req_secret);
		queryMap.put("data_secret", data_secret);
		bulidSignAndGenUrl(queryMap, cardback);
		return response;
	}

}
