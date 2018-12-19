package junit.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.ssh.crud.pojo.Department;
import com.atguigu.ssh.crud.pojo.Employee;
import com.atguigu.ssh.crud.service.EmployeeService;


public class TestEmployeeService {

	ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
	EmployeeService employeeService = (EmployeeService) ioc.getBean("employeeService");
	
	@Test
	public void testQueryAllEmp() throws Exception{
		List<Employee> queryAllEmp = employeeService.queryAllEmp();
		for (Employee employee : queryAllEmp) {
			System.out.println(employee);
		}
	}
	
	@Test
	public void testSaveEmp() throws Exception{
		Employee emp = new Employee(null,"张三");
		
		Department department = new Department(1,"教学部");
		emp.setDepartment(department);
		
		employeeService.saveEmp(emp);
	}
	
}
