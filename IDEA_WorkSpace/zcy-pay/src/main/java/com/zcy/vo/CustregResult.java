package com.zcy.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CustregResult implements Serializable {
	private static final long serialVersionUID = 2441403352125548470L;

	private String resp_desc; // 结果描述
	private String resp_code; //结果状态码


}
