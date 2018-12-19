package com.atguigu.ssh.crud.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atguigu.ssh.crud.pojo.Department;
import com.atguigu.ssh.crud.pojo.Employee;

@Repository
@SuppressWarnings("all")
public class EmployeeDao {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<Employee> queryAllEmp() throws Exception {		
		return sessionFactory.getCurrentSession().createQuery("FROM Employee e LEFT JOIN FETCH e.department").list() ;
	}
	
	public List<Department> queryAllDept() throws Exception {		
		return sessionFactory.getCurrentSession().createQuery("FROM Department").list() ;
	}
	
	public void saveEmp(Employee emp) throws Exception {		
		sessionFactory.getCurrentSession().save(emp);
	}
	
	public Employee getEmp(Integer empId) throws Exception {		
		return (Employee)sessionFactory.getCurrentSession().get(Employee.class,empId) ;
	}
	
	public void updateEmp(Employee emp) throws Exception {
		sessionFactory.getCurrentSession().update(emp);		
	}
	
	public void deleteEmp(Employee emp) throws Exception {
		sessionFactory.getCurrentSession().delete(emp);		
	}
}
