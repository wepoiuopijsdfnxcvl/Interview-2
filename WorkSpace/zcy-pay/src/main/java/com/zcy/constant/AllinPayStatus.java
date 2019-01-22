package com.zcy.constant;

public enum AllinPayStatus {
	HTTP_NOT_ALLOWED(9, "HTTP方法被禁止"),
	SERVICE_UNAVAILABLE(10, "服务不可用"),
	INSUFFICIENT_ISV_PERMISSIONS(11, "合作伙伴权限不足"),
	REMOTE_SERVICE_ERROR(12, "远程服务出错"),
	MISSING_METHOD(21, "缺少方法名参数"),
	INVALID_METHOD(22, "不存在的方法名"),
	INVALID_FORMAT(23, "无效数据格式"),
	MISSING_SIGN(24, "缺少签名参数"),
	INVALID_SIGN(25, "无效签名"),
	MISSING_APPKEY(28, "缺少AppKey参数"),
	INVALID_APP_KEY(29, "无效的AppKey参数"),
	MISSING_TIMESTAMP(30, "缺少时间戳参数"),
	INVALID_TIMESTAMP(31, "非法的时间戳参数"),
	MISSING_VERSION(32, "缺少版本参数"),
	INVALID_VERSION(33, "非法的版本参数"),
	MISSING_SIGN_VERSION(34, "缺少签名版本号"),
	INVALID_SIGN_VERSION(35, "非法的签名版本号"),
	MISSING_ORDER_ID(36, "交易订单号order_id为空"),
	INVALID_ORDER_ID(37, "交易订单号order_id已存在"),
	MISSING_PARAMETER(50, "缺少必选参数"),
	INVALID_PARAMETER(51, "非法的参数"),
	ERROR_PARAMETER(52, "参数错误"),
	BUSINESS_ERROR(53, "远程服务业务错误"),
	;
	private Integer status;
	private String message;

	AllinPayStatus(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public static AllinPayStatus stateOf(int index) {
		for (AllinPayStatus allinPayStatus : values()) {
			if (allinPayStatus.getStatus() == index) {
				return allinPayStatus;
			}
		}
		return null;
	}
}
