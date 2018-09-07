package cn.test.byteStream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;

/**
 * @author Darrick_AZ 字节输入流测试
 */
public class InputStreamTest {
	public static void main(String[] args) {
		try {
			String path = "E:" + File.separator + "hello.txt";

			String newPath = "E:" + File.separator + "hello01.txt";
			File file = new File(path);
			InputStream in = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			// 读取单个字节 
			// int read = in.read();
			// 读取到byte数组中 读取到几个就返回几个字节 没有读到就是-1
			// in.read(b);
			// in.close();
			// 效率提升
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			String str = "";
			while ((str = bufferedReader.readLine()) != null) {
				System.out.println(str);
			}
			// bufferedReader.close();

			FileOutputStream fos = new FileOutputStream(new File(newPath));
			FileInputStream fis = new FileInputStream(new File(path));
			byte[] bytes = new byte[1024];
			int len=0;
			while ((len =fis.read(bytes)) != -1) {
				fos.write(bytes,0,len);
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write("sdfsdfsdfsfsvvvvvvv");
			//刷新缓冲流 作用是在缓冲区没有装满的情况下迫使其进行硬盘写入操作,避免了在close信息丢失的情况
			bw.flush();
			//一点要关流
			bw.close();
			//String readStr = new String(b);
			// System.out.println(readStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
