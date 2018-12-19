package com.atguigu.ssh.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.ssh.crud.dao.EmployeeDao;
import com.atguigu.ssh.crud.pojo.Department;
import com.atguigu.ssh.crud.pojo.Employee;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	public List<Employee> queryAllEmp() throws Exception {		
		return employeeDao.queryAllEmp();
	}
	
	public List<Department> queryAllDept() throws Exception {	
		return employeeDao.queryAllDept();
	}
	
	public void saveEmp(Employee emp) throws Exception {	
		employeeDao.saveEmp(emp);
	}
	
	public Employee getEmp(Integer empId) throws Exception {	
		return employeeDao.getEmp(empId);
	}
	
	public void updateEmp(Employee emp) throws Exception {
		employeeDao.updateEmp(emp);
	}
	
	public void deleteEmp(Integer empId) throws Exception {
		Employee emp = employeeDao.getEmp(empId);
		if(emp!=null){
			employeeDao.deleteEmp(emp);
		}		
	}
	
}
