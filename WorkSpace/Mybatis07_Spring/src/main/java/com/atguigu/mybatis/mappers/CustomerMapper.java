package com.atguigu.mybatis.mappers;

import com.atguigu.mybatis.entities.Customer;

public interface CustomerMapper {
	
	Customer getCustomerById(Integer custId);

}
