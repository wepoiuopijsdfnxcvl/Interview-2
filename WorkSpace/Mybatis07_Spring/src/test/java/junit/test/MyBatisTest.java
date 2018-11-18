package junit.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.mybatis.entities.Customer;
import com.atguigu.mybatis.service.CustomerService;


public class MyBatisTest {
	
	private ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-context.xml");
	
	@Test
	public void testCustomerService() {
		CustomerService customerService = ioc.getBean(CustomerService.class);
		Customer customer = customerService.getCustomerById(4);
		System.out.println(customer);
	}
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ioc.getBean(DataSource.class);
		Connection connection = dataSource.getConnection();
		
		System.out.println(connection);
	}

}
