package com.taotao.fastdfs;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.taotao.utils.FastDFSClient;
 
public class TestFastDFS {
	@Test
	public void testUploadFile() throws Exception{
		//1.向工程添加jar包
		//2.创建一个配置文件，配置tracker服务器地址
		//3.加载配置文件
		ClientGlobal.init("C:\\Users\\Darrick\\Desktop\\interview\\WorkSpace\\taotao-manager-web\\src\\main\\resources\\resource\\fast-dfs.conf");
		//4.创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		//5.使用TrackerClient对象获得trackerserver对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		//6.创建一个StorageServer的引用，我们用null就可以
		StorageServer storageServer = null;
		//7.创建一个StorageClient对象。trackerserver、StorageServer两个参数
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//8.使用StorageClient对象上传文件
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair("fileName", "2");
		metaList[1] = new NameValuePair("createTime", "2017-04-13 16:01:00");
		metaList[2] = new NameValuePair("createUser", "zhangsan");
		String[] upload_files = storageClient.upload_file("C:\\Users\\Darrick\\Desktop\\interview\\WorkSpace\\Tools\\a.jpg", "jpg", metaList);
		for(String file : upload_files){
			System.out.println(file);
		}
	}
	
	@Test
	public void testFastDfsClient() throws Exception {
	    FastDFSClient fastDFSClient = new FastDFSClient("C:/Users/Darrick/Desktop/interview/WorkSpace/taotao-manager-web/src/main/resources/resource/fast-dfs.conf");
	    String string = fastDFSClient.uploadFile("C:/Users/Darrick/Desktop/interview/WorkSpace/Tools/a.jpg");
	    // 注意，你再上传一遍，刚才你上传的那个图片就已经丢了，它已经存在服务器上了，你再也访问不到了
	    System.out.println(string);
	}
}
