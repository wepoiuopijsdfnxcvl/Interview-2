package com.atguigu.ssh.crud.pojo;

public class Employee {

	private Integer empId ;
	private String empName ;
	
	private Department department ;//多对一

	public Employee() {
		super();
	}

	public Employee(Integer empId, String empName) {
		super();
		this.empId = empId;
		this.empName = empName;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName
				+ ", department=" + department + "]";
	}
	
	
	
}
