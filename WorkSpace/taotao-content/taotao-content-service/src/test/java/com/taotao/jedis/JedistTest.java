package com.taotao.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.content.jedis.JedisClient;

/**
 * spring 集成jedis
 * 
 * @author Darrick
 *
 */
public class JedistTest {

	@Test
	public void testJedisClientPool() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-redis.xml");
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		jedisClient.set("client", "hello");
		String result = jedisClient.get("client");
		System.out.println(result);
	}

}
