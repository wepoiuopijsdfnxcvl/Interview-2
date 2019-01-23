package com.zcy.exception;

public class WhiteIpException extends Exception {

	private static final long serialVersionUID = 6055753422221329630L;

	public WhiteIpException() {
		super();
	}

	public WhiteIpException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public WhiteIpException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public WhiteIpException(String arg0) {
		super(arg0);
	}

	public WhiteIpException(Throwable arg0) {
		super(arg0);
	}

}
