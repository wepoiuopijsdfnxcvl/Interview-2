package com.zcy.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 通联返回参数
 **
 * @author breeze
 * @date 2019-01-22 09:34
 * @Description
 */

@NoArgsConstructor
@Getter
@Setter
public class AllinResponse implements Serializable {

	private static final long serialVersionUID = 1076545671443276556L;

	private String res_timestamp; // 时间戳
	private String code; // 平台错误码
	private String msg; // 平台提示消息
	private String res_sign; // 校验签名
	private String sub_code; // 业务错误码
	private String sub_msg; // 业务提示消息
	private Args args; // 签名参数

}
