package com.zcy.exception;

public class LimitIPRequestException extends Exception{

	/**
	 *
	 */
	private static final long serialVersionUID = 7195348250511817390L;
	private int status = -1;

	public LimitIPRequestException() {
		super();
	}

	public LimitIPRequestException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public LimitIPRequestException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public LimitIPRequestException(String arg0) {
		super(arg0);
	}
	public LimitIPRequestException(String arg0, int status) {
		super(arg0);
		this.status=status;
	}


	public LimitIPRequestException(Throwable arg0) {
		super(arg0);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	
}
