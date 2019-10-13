package com.xujiabao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis.xml")
public class RedisTest {

	@Autowired
	RedisTemplate redisTemplate;
	
	@Test
	public void testJson() {
		Types types1 = new Types();
		types1.setId(1);
		types1.setName("电器");
		String jsonString = JSON.toJSONString(types1);
		//System.out.println(jsonString);
		redisTemplate.opsForValue().set("jsonString", jsonString);
		String redisJson = (String) redisTemplate.opsForValue().get("jsonString");
		System.out.println(redisJson);
		Types type333 = JSON.parseObject(redisJson,Types.class);
		System.out.println(type333);
	}
	
	
	@Test
	public void testRedis() {
		redisTemplate.opsForValue().set("spring2redis", "hello redis",10,TimeUnit.SECONDS);
	//	redisTemplate.opsForValue().set("spring2redis", "hello redis");
	//	redisTemplate.opsForValue().set("spring2redis","hello redis");
	//	redisTemplate.opsForValue().append("appendString", "hello String");
	//	redisTemplate.opsForValue().append("appendString", "i am a coder");
		
		String string = (String) redisTemplate.opsForValue().get("spring2redis");
	//	String string = redisTemplate.opsForValue().get("appendString");
	//	redisTemplate.delete("spring2redis");
		System.out.println(string);
	}
	
	@Test  
	public void testRedisString() {
		/*redisTemplate.opsForList().leftPush("mylist", "a");
		String ele = redisTemplate.opsForList().rightPop("mylist");
		System.out.println(ele); */
		
		ArrayList<Types> list = new ArrayList<Types>();
		Types types1 = new Types();
		types1.setId(1);
		types1.setName("电器");
		
		Types types2 = new Types();
		types2.setId(2);  
		types2.setName("图书");
		
		Types types3 = new Types();
		types3.setId(5);
		types3.setName("水果");
		
		list.add(types1); 
		list.add(types2);
		list.add(types3);
		
		redisTemplate.opsForList().leftPush("mylist", list);
		ArrayList<Types> redislist= (ArrayList<Types>) redisTemplate.opsForList().rightPop("mylist");
		for (Types types : redislist) {
			System.out.println(types);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRedisHash() {
		 Map<String, Types> map = new HashMap<String, Types>();
		 
		 Types types1 = new Types();
			types1.setId(1); 
			types1.setName("电器");
			
			Types types2 = new Types();
			types2.setId(2);  
			types2.setName("图书");
			
			Types types3 = new Types();
			types3.setId(5);
			types3.setName("水果");
			
			map.put(1 + "", types1);
			map.put(2 + "", types2);
			map.put(3 + "", types3);
			
			redisTemplate.opsForHash().putAll("myhash", map);
			Map entries = redisTemplate.opsForHash().entries("myhash");
			Set entrySet = entries.entrySet();
			for (Object object : entrySet) {
				System.out.println(object);
			}
			
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRedisSet() {
		
			HashSet<Types> hashSet = new HashSet<Types>();
            List<Types> types = new ArrayList<Types>();
            Types types1 = new Types();
            types1.setId(1);
            types1.setName("电器");

            Types types2 = new Types();
            types2.setId(2);
            types2.setName("图书");

            Types types3 = new Types();
            types3.setId(5);
            types3.setName("水果");

            types.add(types1);
            types.add(types2);
            types.add(types3);
            
            hashSet.add(types1);
            hashSet.add(types2);
            hashSet.add(types3);
            System.err.println(hashSet);
            
            redisTemplate.opsForSet().add("myset", types.toArray());
            Set members = redisTemplate.opsForSet().members("myset");
            for (Object object : members) {
				System.out.println(object);
			}
	}
} 
