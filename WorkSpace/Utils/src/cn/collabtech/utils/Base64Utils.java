package cn.collabtech.utils;
public class Base64Utils {
	
	public static void main(String[] args) {
		String str = "YWRtaW4xMjNA";
		//编码
		String encode = encode(str);
		System.out.println(encode);
		//解码
		String decode = decode("WnoxMjM0NTY3ODlA");
		System.out.println(decode);
		
	}
	
	/**
	 * 编码 转密码
	 * @param pwd
	 * @return
	 */
	public static String encode(String pwd)
	{
		return new String(org.springframework.util.Base64Utils.encode(pwd.getBytes()));
	}
	/**
	 * 解码
	 * 转铭文
	 * @param pwd
	 * @return
	 */
	public static String decode(String pwd)
	{
		return new String(org.springframework.util.Base64Utils.decode(pwd.getBytes()));
	}
	
}