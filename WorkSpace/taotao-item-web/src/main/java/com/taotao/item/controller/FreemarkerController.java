package com.taotao.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
public class FreemarkerController {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@RequestMapping("/genHtml")
	public String genHtml() throws Exception {
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("hello.ftl");
		Map map = new HashMap();
		map.put("hello", "freemarker整合spring测试");
		FileWriter out = new FileWriter(new File("C:/Users/Darrick/Desktop/interview/WorkSpace/temp/freemarker/out.html"));
		template.process(map, out);
		out.close();
		return "ok";
	}

}
