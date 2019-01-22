package com.zcy.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 通联虚拟卡注册返回
 **
 * @author breeze
 * @date 2019-01-22 09:35 
 * @Description
 */

@Getter
@Setter
public class CardRegisterResponse extends AllinResponse {

	private static final long serialVersionUID = 145391077517952591L;

	private String card_id; // 卡id
	private CustregResult result;

}
