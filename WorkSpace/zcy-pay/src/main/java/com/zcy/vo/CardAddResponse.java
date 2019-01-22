package com.zcy.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardAddResponse extends AllinResponse {

	private static final long serialVersionUID = 4914796989717145681L;

	private String trans_no; // 交易流水号
	private String order_id; // 交易订单号
	private ResultInfo result_info;


}
