package com.zcy.exception;

import com.zcy.vo.BaseResponse;

public class SignException extends Exception {
	private static final long serialVersionUID = 7195348250511817390L;
	private int status = -1;

	private BaseResponse baseResponse;

	public SignException() {
		super();
	}

	public SignException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public SignException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SignException(BaseResponse baseResponse) {
		this.baseResponse = baseResponse;
	}

	public SignException(String arg0, int status) {
		super(arg0);
		this.status = status;
	}

	public SignException(Throwable arg0) {
		super(arg0);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BaseResponse getBaseResponse() {
		return baseResponse;
	}

	public void setBaseResponse(BaseResponse baseResponse) {
		this.baseResponse = baseResponse;
	}
}
