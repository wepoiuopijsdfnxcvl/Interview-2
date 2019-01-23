package com.zcy.utils;

import com.zcy.vo.ServiceResponse;

public class ResponseUtils {

	public static ServiceResponse buildSuccessResult(Object obj) {
		ServiceResponse response = new ServiceResponse();
		response.setData(obj);
		return response;
	}

	public static void exception(ServiceResponse response, String msg, int status) {
		response.setData(null);
		response.setStatus(status);
		response.setMessage(msg);
	}

}
