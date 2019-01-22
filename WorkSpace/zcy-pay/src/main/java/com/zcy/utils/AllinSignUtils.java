package com.zcy.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


/**
 * 生成通联签名和URL的方法
 **
 * @author breeze
 * @date 2019-01-15 18:02
 * @Description
 */
public class AllinSignUtils {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 生成签名
	 * 
	 * @param queryMap  请求报文
	 * @param secretKey 请求密钥
	 * @return
	 * @throws Exception
	 */
	public static TreeMap<String, String> addSign(TreeMap<String, String> queryMap, String secretKey) throws Exception {
		Set<Entry<String, String>> paramSet = queryMap.entrySet();

		StringBuilder query = new StringBuilder();
		query.append(secretKey);
		// System.out.println(query.toString() + "\r\n");

		for (Entry<String, String> param : paramSet) {
			if ((param.getKey() != null && param.getValue() != null)) {
				query.append(param.getKey()).append(param.getValue());
			}
		}
		query.append(secretKey);

		byte[] bytes = encryptMD5(query.toString());
		queryMap.put("sign", byte2hex(bytes));
		return queryMap;
	}

	/**
	 * MD5 加密数据
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	private static byte[] encryptMD5(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes("UTF-8"));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse);
		}
		return bytes;
	}

	/**
	 * 把二进制转化为大写的十六进制
	 * 
	 * @param bytes
	 * @return
	 */
	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}

	/**
	 * 将HashMap参数组装成字符串
	 * 
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String parseParams(Map<String, String> map) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		if (map != null) {
			for (Entry<String, String> e : map.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				// 需要将特殊字符或中文转换（比如加密后的password）
				sb.append(java.net.URLEncoder.encode(e.getValue(), "UTF-8"));
				sb.append("&");
			}
		}
		String sbStr = sb.toString();
		return sbStr.substring(0, sbStr.length() - 1);
	}

	/**
	 * 生成URL
	 * 
	 * @param queryMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String genUrl(TreeMap<String, String> queryMap) throws UnsupportedEncodingException {
		String url = "http://wxtest.ulinkpay.com/aop/rest";
		String param = parseParams(queryMap);
		String urlNameString = url + "?" + param;
		// System.out.println(urlNameString);
		return urlNameString;
	}

	public static void main(String[] args) throws Exception {
		TreeMap<String, String> queryMap = new TreeMap<String, String>();
		queryMap.put("method", "allinpay.ppcs.cardinfo.get");
		queryMap.put("timestamp", sdf.format(new Date()));
		queryMap.put("format", "json");
		queryMap.put("app_key", "test");
		queryMap.put("v", "1.0");
		queryMap.put("sign_v", "1");
		queryMap.put("card_id", "8686860211000000516");
		TreeMap<String, String> map = addSign(queryMap, "test");
		String genUrl = genUrl(map);
		System.out.println(genUrl);
	}
}
