package com.zcy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import com.zcy.aspect.HttpServletRequestReplacedFilter;
import org.springframework.context.annotation.ImportResource;

/**
 * @ServletComponentScan
 * 扫描servlet，filter组件
 */
@SpringBootApplication
@MapperScan("com.zcy.dao")
@ImportResource(locations = { "classpath:druid-bean.xml" })
public class ZcyPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZcyPayApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<HttpServletRequestReplacedFilter> httpServletRequestReplacedRegistration() {
		FilterRegistrationBean<HttpServletRequestReplacedFilter> registration = new FilterRegistrationBean<HttpServletRequestReplacedFilter>();
		registration.setFilter(new HttpServletRequestReplacedFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("httpServletRequestReplacedFilter");
		registration.setOrder(1);
		return registration;
	}
}
