package com.zcy.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardBackResponse extends AllinResponse {

	private static final long serialVersionUID = -4224962349381603753L;

	private String order_id; // 订单号
	private String trans_no;// 通联流水号
	private String ori_order_id;// 原交易订单号
	private String mer_id; // 商户号
	private String trans_date;// 交易日期
	private String amount;// 退货金额（分为单位，如100代表1元）
	private String back_order_id;// 退货订单号


}
