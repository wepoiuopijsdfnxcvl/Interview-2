package com.zcy.utils;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {

	/**
	 * 获取当前请求的IP地址
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {

		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip.length() > 15) {
			//"***.***.***.***".length() = 15
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

}