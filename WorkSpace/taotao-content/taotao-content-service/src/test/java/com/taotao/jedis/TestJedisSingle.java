package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedisSingle {

	@Test
	public void testJedisSingle() {
		// 第一步，创建jedis对象，指定服务的ip及端口
		Jedis jedis = new Jedis("192.168.188.128", 6379);
		// 第二步，使用jedis对象操作数据库，每个redis对应一个方法
		jedis.set("mytest", "1000");
		// 第三部，打印结果
		String result = jedis.get("mytest");
		System.out.println(result);
		// 第四部，关闭jedis连接
		jedis.close();
	}

	@Test
	public void testJedisPool() {
		// 创建一个连接池对象
		JedisPool jedisPool = new JedisPool("192.168.188.128", 6379);
		// 从连接池获取连接
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get("mytest");
		System.out.println(result);
		// 每次使用jedis之后需要关闭，连接池回收资源
		jedis.close();
		// 系统结束前关闭连接池
		jedisPool.close();
	}

	 //集群版
	@Test
	public void testJedisCluster() {
		// 创建构造参数set类型，集合中每个元素是HostAndPort类型
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		// 向集合中添加节点
		nodes.add(new HostAndPort("192.168.188.128", 7001));
		nodes.add(new HostAndPort("192.168.188.128", 7002));
		nodes.add(new HostAndPort("192.168.188.128", 7003));
		nodes.add(new HostAndPort("192.168.188.128", 7004));
		nodes.add(new HostAndPort("192.168.188.128", 7005));
		nodes.add(new HostAndPort("192.168.188.128", 7006));
		// 创建JedisCluster对象
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("jedisCluster:sdfdsfd:wew:rere:rer", "123456");
		String result = jedisCluster.get("jedisCluster:sdfdsfd");
		System.out.println(result);
		// 系统结束前关闭jedisCluster对象
		jedisCluster.close();
	}
}
