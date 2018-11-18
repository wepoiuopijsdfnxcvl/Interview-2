package com.atguigu.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.mybatis.entities.Customer;
import com.atguigu.mybatis.mappers.CustomerMapper;

public class CustomerService {
	
	@Autowired
	private CustomerMapper customerMapper;
	
	public Customer getCustomerById(Integer custId) {
		return customerMapper.getCustomerById(custId);
	}

}
