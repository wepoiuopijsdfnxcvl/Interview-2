package com.zcy.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResultInfo implements Serializable {

	private static final long serialVersionUID = 3691299122987390726L;

	private String amount; // 充值金额
	private String prdt_no; // 产品编号
	private String validity_date; // 卡截止有效期
	private String top_up_way; // 充值方式
	private String valid_balance; // 可用余额
	private String card_id; // 卡号
	private String account_balance; // 账户余额
	private String desn; // 交易名称


}
