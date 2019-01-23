package com.zcy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zcy.annotation.LimitRequest;
import com.zcy.annotation.NoNeedCheck;
import com.zcy.dao.UserVirtualCardMapper;
import com.zcy.model.UserVirtualCard;

@RestController
@RequestMapping("/userVirtualCard")
public class UserVirtualCardController {

	@Autowired
	private UserVirtualCardMapper userVirtualCardMapper;

	@LimitRequest(limitCounts = 150, timeSecond = 1)
	@NoNeedCheck
	@RequestMapping(value = "/queryInfo", method = RequestMethod.POST)
	public UserVirtualCard queryInfo(@RequestBody UserVirtualCard param) {
		UserVirtualCard card = userVirtualCardMapper.selectByPrimaryKey(param.getVirtualCardId());
		return card;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String test() {
		return "hello thank you";
	}

}
