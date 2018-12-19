package com.atguigu.ssh.crud.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.atguigu.ssh.crud.pojo.Employee;
import com.atguigu.ssh.crud.service.EmployeeService;

@Controller
@Scope("prototype")
public class EmployeeAction implements RequestAware{

	@Autowired
	private EmployeeService employeeService ;
	private Map<String, Object> request;
	
	public String queryAll() throws Exception{
		List<Employee> empList = employeeService.queryAllEmp();
		request.put("empList", empList);
		return "success";
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request ;
	}
	
}
