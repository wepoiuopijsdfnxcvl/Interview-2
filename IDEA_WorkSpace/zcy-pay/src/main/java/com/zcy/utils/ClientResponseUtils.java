package com.zcy.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.zcy.constant.ClientCommon;
import com.zcy.vo.BaseResponse;
import com.zcy.vo.BaseSign;

public class ClientResponseUtils {

	public static BaseResponse buildSuccessResult(BaseResponse response, Object obj, BaseSign baseSign,
			String secretKey, String message, int status) {
		response.setData(obj);
		if (baseSign == null) {
			baseSign = new BaseSign();
		}
		baseSign.setSign(null);
		String nonceStr = RSAUtils.getNonceStr();
		baseSign.setNonceStr(nonceStr);
		baseSign.setTimestamp(System.currentTimeMillis());
		// 进行返回签名
		if (StringUtils.isNotEmpty(secretKey)) {
			String data = JSON.toJSONString(obj);
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put(ClientCommon.DATA_KEY, data);
			paramMap.put(ClientCommon.API_KEY, baseSign.getApiKey());
			paramMap.put(ClientCommon.NONCE_STR, baseSign.getNonceStr());
			paramMap.put(ClientCommon.TIME_STAMP, baseSign.getTimestamp() + "");
			String sign = SignUtils.getSign(paramMap, secretKey);
			baseSign.setSign(sign);
		}
		baseSign.setApiKey(baseSign.getApiKey());
		response.setStatus(status);
		response.setMessage(message);
		response.setBaseSign(baseSign);
		return response;
	}

	public static void exception(BaseResponse response, String msg, int status) {
		response.setData(null);
		response.setStatus(status);
		response.setMessage(msg);
	}

	public static void exceptionWithData(BaseResponse response, String msg, int status) {
		response.setStatus(status);
		response.setMessage(msg);
	}

}