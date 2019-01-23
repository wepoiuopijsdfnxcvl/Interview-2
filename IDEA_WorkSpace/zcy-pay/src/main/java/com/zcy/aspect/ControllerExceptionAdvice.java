package com.zcy.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcy.constant.RequestStatus;
import com.zcy.exception.LimitIPRequestException;
import com.zcy.exception.SignException;
import com.zcy.exception.WhiteIpException;
import com.zcy.utils.ResponseUtils;
import com.zcy.vo.BaseResponse;

@ControllerAdvice
public class ControllerExceptionAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionAdvice.class);

	@ExceptionHandler(LimitIPRequestException.class)
	@ResponseBody
	public BaseResponse limitException(HttpServletRequest request, LimitIPRequestException ex) {
		BaseResponse response = new BaseResponse();
		LOGGER.error("limitException ： 请求频率过高");
		ResponseUtils.exception(response,
				StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : RequestStatus.REQUEST_TOO_FAST.getMessage(),
				StringUtils.isNotEmpty(ex.getMessage()) ? RequestStatus.PARAM_REQUIRED.getStatus()
						: RequestStatus.REQUEST_TOO_FAST.getStatus());
		return response;
	}

	@ExceptionHandler(SignException.class)
	@ResponseBody
	public BaseResponse signException(HttpServletRequest request, SignException ex) {
		BaseResponse response = null;
		try {
			LOGGER.error("signException ： ");
			response = ex.getBaseResponse();
			if (response == null) {
				response = new BaseResponse();
				ResponseUtils.exception(response, RequestStatus.SIGN_INVALID.getMessage(),
						RequestStatus.SIGN_INVALID.getStatus());
			}
		} catch (Exception e) {
			LOGGER.error("signException : " + e);
			ResponseUtils.exception(response, RequestStatus.SIGN_INVALID.getMessage(),
					RequestStatus.SIGN_INVALID.getStatus());
		}
		return response;
	}

	@ExceptionHandler(WhiteIpException.class)
	@ResponseBody
	public BaseResponse whiteIpException(HttpServletRequest request, WhiteIpException ex) {
		BaseResponse response = new BaseResponse();
		ResponseUtils.exception(response, RequestStatus.INVALID_IP.getMessage(), RequestStatus.INVALID_IP.getStatus());
		return response;
	}
}