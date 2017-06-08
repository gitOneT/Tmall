package cn.e3mall.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * 
 * <p>Title: TestJedies</p>
 * <p>Description:单机版Jedis的测试展示 </p>
 * @version 1.0
 */
public class TestJedies {

	@Test
	public void runJedis(){
		//1.创建一个Jedis对象,需要指定服务的ip 和端口
		Jedis jedis=new Jedis("192.168.25.129", 6379);
		//2.使用Jedids 对象操作数据库.每个redies命令对应一个方法
		String string = jedis.set("testJedis", "hehe");
		String string2 = jedis.get("testJedis");
		//3.打印结果, 
		System.out.println(string);
		System.out.println(string2);
		//关闭Jedis
		jedis.close();
	}
	
	/**
	 * 单机版使用JedisPool链接池测试
	 */
	@Test
	public void TestJedisPool(){
		//1.创建一个JedisPool链接池对象.需要指定服务的ip 及端口
		JedisPool jedisPool=new JedisPool("192.168.25.129", 6379);
		//2.从JedisPool中获取jedis对象,
		Jedis jedis = jedisPool.getResource();
		//3.使用jedis操作redis服务器
		String set = jedis.set("pool2", "JedisPool主键");
		String string = jedis.get("pool2");
		System.out.println(set);
		System.out.println(string);
		//4.操作完毕后关闭jedis对象, 一遍让连接池回收资源.  --这一步很重要,不然 链接池中没有redis的话就会抛异常
		jedis.close();
		//5.关闭JedisPool对象
		jedisPool.close();
	}
	
	/**
	 * 链接集群版
	 */
	@Test
	public void TestRedisCluster(){
		//使用JedisCluster 对象 .需要使用Set<HostAndPost>参数.Redis 节点列表
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.129", 7001));
		nodes.add(new HostAndPort("192.168.25.129", 7002));
		nodes.add(new HostAndPort("192.168.25.129", 7003));
		nodes.add(new HostAndPort("192.168.25.129", 7004));
		nodes.add(new HostAndPort("192.168.25.129", 7005));
		nodes.add(new HostAndPort("192.168.25.129", 7006));
		//直接使用JedisCluster对象操作redis .在系统中单例存在
		JedisCluster jedisCluster=new JedisCluster(nodes);
		jedisCluster.set("hello", "123");
		String string = jedisCluster.get("hello");
		//打印结果
		System.out.println(string);
		//关闭JedisCluter对象
		jedisCluster.close();
	}
	
}
