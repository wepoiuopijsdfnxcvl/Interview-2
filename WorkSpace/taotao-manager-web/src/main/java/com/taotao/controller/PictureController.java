package com.taotao.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.utils.FastDFSClient;

@Controller
public class PictureController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	/*
	 * @RequestMapping("/pic/upload")
	 * 
	 * @ResponseBody public Map uploadPic(MultipartFile uploadFile) { // 首先接收页面上传的文件
	 * try { byte[] content = uploadFile.getBytes(); // 取出文件的扩展名 String
	 * originalFilename = uploadFile.getOriginalFilename(); String exe =
	 * originalFilename.substring(originalFilename.lastIndexOf(".")); //
	 * 把这个文件内容上传到图片服务器 FastDFSClient fastDFSClient = new FastDFSClient(
	 * "C:/Users/Darrick/Desktop/interview/WorkSpace/taotao-manager-web/src/main/resources/resource/fast-dfs.conf"
	 * ); String url = fastDFSClient.uploadFile(content, exe); //
	 * 从配置文件中取出图片服务器返回的url // 创建返回结果对象 Map result = new HashMap<>();
	 * result.put("error", 0); result.put("url", IMAGE_SERVER_URL + url); // 返回结果
	 * return result; } catch (Exception e) { e.printStackTrace(); Map result = new
	 * HashMap<>(); result.put("error", 1); result.put("message", "图片上传失败"); return
	 * result; } }
	 */

	/**
	 * 为了兼容火狐浏览器不能解析Map返回值的方法
	 */
	@RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
	@ResponseBody
	public String uploadPic(MultipartFile uploadFile) {
		// 首先接收页面上传的文件
		try {
			byte[] content = uploadFile.getBytes();
			// 取出文件的扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String exe = originalFilename.substring(originalFilename.lastIndexOf("."));
			// 把这个文件内容上传到图片服务器
			FastDFSClient fastDFSClient = new FastDFSClient(
					"C:/Users/Darrick/Desktop/interview/WorkSpace/taotao-manager-web/src/main/resources/resource/fast-dfs.conf");
			String url = fastDFSClient.uploadFile(content, exe);
			// 从配置文件中取出图片服务器返回的url
			// 创建返回结果对象
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", IMAGE_SERVER_URL + url);
			// 返回结果
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
		}
	}

}
