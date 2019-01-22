package com.zcy.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alibaba.fastjson.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserVirtualCardControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getInfo() throws Exception {
		MvcResult result = mockMvc.perform(post("/userVirtualCard/queryInfo").param("id", "1"))
				.andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testSave() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("address", "合肥");
		map.put("name", "测试");
		map.put("age", 50);

		MvcResult result = mockMvc
				.perform(post("/save").contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(map)))
				.andExpect(status().isOk())// 模拟向testRest发送get请求
				.andReturn();// 返回执行请求的结果

		System.out.println(result.getResponse().getContentAsString());
	}

}