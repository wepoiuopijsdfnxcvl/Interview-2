package com.zcy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PayLog {
	private Integer logId;

	private String userId;

	private Date createTime;

	private Integer spendTime;

	private String method;

	private String userAgent;

	private String userIp;

	private String url;

	private String optContent;

	private String optResult;

}