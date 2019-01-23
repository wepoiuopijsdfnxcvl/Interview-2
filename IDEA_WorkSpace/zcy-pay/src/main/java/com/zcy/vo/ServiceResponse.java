package com.zcy.vo;

import java.io.Serializable;

import com.zcy.constant.RequestStatus;
import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @author tom
 *
 *         2018年11月16日
 */
@Getter
@Setter
public class ServiceResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1644293476601330805L;
	/**
	 * 
	 */
	private int status = RequestStatus.SUCCESS.getStatus();
	private String message = "";
	private Object data;

}
