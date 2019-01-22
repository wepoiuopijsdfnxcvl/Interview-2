package com.zcy.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.nio.charset.Charset;

public class HttpClientHelper {

    private static final Logger logger = Logger.getLogger(HttpClientHelper.class);

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

    public static String doGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
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

        } finally {
            httpGet.releaseConnection();
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("failed to connect" + url);
                }
            }
        }
        return content;
    }

    public static String doPost(String url, Object object) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
        httpPost.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        String content = null;
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
                httpPost.setEntity(stringEntity);
            }
            response = httpClient.execute(httpPost);

            HttpEntity respEntity = response.getEntity();
            if (respEntity != null && respEntity.getContentLength() <= Integer.MAX_VALUE) {
                content = EntityUtils.toString(respEntity);
            }

        } finally {
            httpPost.releaseConnection();
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("failed to connect" + url, e);
                }
            }
        }
        return content;
    }

    /**
     * 添加Authorization
     * @param url
     * @param object
     * @return
     * @throws Exception
     */
    public static String doPostWithAuthorization(String url, Object object,String authorization) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Authorization",authorization);
        CloseableHttpResponse response = null;
        String content = null;
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
                httpPost.setEntity(stringEntity);
            }
            response = httpClient.execute(httpPost);

            HttpEntity respEntity = response.getEntity();
            if (respEntity != null && respEntity.getContentLength() <= Integer.MAX_VALUE) {
                content = EntityUtils.toString(respEntity);
            }

        } finally {
            httpPost.releaseConnection();
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("failed to connect" + url, e);
                }
            }
        }
        return content;
    }

    public static int doPut(String url, Object object) throws Exception {
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
        httpPut.setConfig(requestConfig);
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
            httpPut.releaseConnection();
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("failed to connect" + url);
                }
            }
        }
        return content;
    }


    public static void doDelete(String url) throws Exception {
        HttpDelete httpDelete = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
        httpDelete.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpDelete);
        } finally {
            httpDelete.releaseConnection();
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (Exception e) {
                    logger.error("failed to connect" + url);
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