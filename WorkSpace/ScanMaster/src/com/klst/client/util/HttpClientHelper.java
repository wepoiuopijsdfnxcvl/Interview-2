package com.klst.client.util;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class HttpClientHelper {

	private static final Logger LOGGER = Log.getLogger(HttpClientHelper.class.getSimpleName());

	private static CloseableHttpClient httpClient = newHttpsClient();

	private static CloseableHttpClient newHttpsClient() {
		CloseableHttpClient client = null;
		try {
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

	public static String doGetWithRole(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Current-User", "admin|" + "Administrator");
//		LOGGER.debug("Executing request " + httpGet.getRequestLine());
		CloseableHttpResponse response = null;
		String content = null;

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		httpGet.setConfig(requestConfig);

		try {
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null && entity.getContentLength() <= Integer.MAX_VALUE) {
				content = EntityUtils.toString(entity, Charset.forName("utf-8"));
			}
//			LOGGER.debug("doGet result:" + content);

		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					LOGGER.debug("failed to connect" + url);
				}
			}
		}
		return content;
	}

	public static String doPost(String url, Object object) throws Exception {
		HttpPost httpPost = new HttpPost(url);
//		LOGGER.info("Executing request " + httpPost.getRequestLine());
		CloseableHttpResponse response = null;
		String content = null;
		try {
			if (null != object) {
				StringEntity stringEntity = new StringEntity(JSON.toJSONString(object), "UTF-8");// 解决中文乱码问题
				stringEntity.setContentEncoding("UTF-8");
				stringEntity.setContentType("application/json");
				httpPost.setEntity(stringEntity);
			}
			response = httpClient.execute(httpPost);

			HttpEntity respEntity = response.getEntity();
			if (respEntity != null && respEntity.getContentLength() <= Integer.MAX_VALUE) {
				content = EntityUtils.toString(respEntity);
			}

		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					LOGGER.warn("failed to connect" + url, e);
				}
			}
		}
		return content;
	}

	public static int doPutWithRole(String url, Object object) throws Exception {
		HttpPut httpPut = new HttpPut(url);
		httpPut.addHeader("Current-User", "admin|" + "Administrator");
//		LOGGER.debug("Executing request " + httpPut.getRequestLine());
		CloseableHttpResponse response = null;
		int content = 500;
		try {
			if (null != object) {
				StringEntity stringEntity = null;
				if (object instanceof String) {
					stringEntity = new StringEntity(object.toString(), "UTF-8");// 解决中文乱码问题
				} else {
					stringEntity = new StringEntity(JSON.toJSONString(object), "UTF-8");// 解决中文乱码问题
				}
				stringEntity.setContentEncoding("UTF-8");
				stringEntity.setContentType("application/json");
				httpPut.setEntity(stringEntity);
			}
			response = httpClient.execute(httpPut);

			content = response.getStatusLine().getStatusCode();

		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					LOGGER.debug("failed to connect" + url);
				}
			}
		}
		return content;
	}

	public static void doDelete(String url) throws Exception {
		HttpDelete httpDelete = new HttpDelete(url);
//		LOGGER.debug("Executing request " + httpDelete.getRequestLine());
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpDelete);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (Exception e) {
					LOGGER.debug("failed to connect" + url);
				}
			}
		}
	}

	public void closeConnection() {
		try {
			if (httpClient != null)
				httpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
