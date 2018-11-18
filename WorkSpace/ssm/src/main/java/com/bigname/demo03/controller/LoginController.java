package com.bigname.demo03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigname.demo03.core.Member;
import com.bigname.demo03.function.IMemberFunction;

@Controller
public class LoginController {
	@Autowired
	IMemberFunction iMemberFunc;
	
	@RequestMapping(value = "/hello")
	public String hello(){
		  System.out.println("接收到请求 ，Hello");
		return "hi";
	}
	
	@RequestMapping(value = "/login")
	public String login(String name, String password){
		try {
			Member member = iMemberFunc.login(name, password);
			if(member == null){
				  System.out.println("登陆失败");
			}else {
				 System.out.println("登陆成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			 System.out.println("登陆成功");
		}
		return null;
	}
	
	@RequestMapping(value = "/getJSON")
	@ResponseBody
	public String getJSON(){
		return "testtetete";
	}
	
	
}
