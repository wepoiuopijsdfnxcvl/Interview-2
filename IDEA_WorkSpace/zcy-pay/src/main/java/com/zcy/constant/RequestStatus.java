package com.zcy.constant;


public enum RequestStatus {

	SUCCESS(0, "成功"),
	FAILED(-1, "失败"),
	NOT_LOGIN(-101,"未登录"),
	MULTI_USER_LOGIN(110,"同一浏览器多账户登录问题"),
	PARAM_INVALID(40001,"字段校验非法，请检查请求参数"),
	PRIVATE_KEY_ABSENCE(40002,"私钥不存在，非法调用"),
	PARAM_REQUIRED(40004,"缺乏参数，请检查请求参数"),
	METHOD_ALLOWED(40103,"不支持的HTTP请求方法，请调整请求方式"),
	INVALID_IP(40200,"非法的ip访问"),
	REQUEST_TOO_FAST(40201,"请求频率太快"),
	REQUEST_LIMIT(40202,"正在处理中，请勿重复操作或稍后再试，请检查是否同一个请求重复调用"),
	IN_PROCESS(40203,"请求频率达到限制，请检查是否代码有问题或联系运营人员确认是否那么高的请求频率"),
	SIGN_INVALID(40204,"签名校验失败"),
	ORDER_ID_REPEAT(40205,"订单号重复"),
	ACCEPTED_SUCCESS(20000,"受理成功"),
	;
	private Integer status;
	private String message;

	RequestStatus(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public static RequestStatus stateOf(int index) {
		for (RequestStatus requestStatus : values()) {
			if (requestStatus.getStatus() == index) {
				return requestStatus;
			}
		}
		return null;
	}
}

