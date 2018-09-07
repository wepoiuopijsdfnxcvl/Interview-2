package cn.collabtech.bytes;

/**
 * @author Darrick_AZ
 * 
 *         进制转换 二进制 1111
 * 
 *         八进制 17
 * 
 *         十进制 15
 * 
 *         十六进制 f（0f）
 * 
 *         二进制转十进制 1*2^3+1*2^2+1*2*1+1*2^0=8+4+2+1=15
 * 
 *         十进制转八进制 15%8 = 1 余 7
 * 
 *         十六进制转十进制 0*16+f = 15
 * 
 */
public class BytesToHexString {

	/**
	 * @Method main
	 * @Description: TODO(这里用 一句话描述这个方法的作用)
	 * @param args 
	 * @Author Darrick
	 * @Return void
	 * @Date 2018年6月5日 上午9:44:25
	 */
	public static void main(String[] args) {
		String str = "北京ABC";
		byte[] bytes = str.getBytes(); 
		for (byte b : bytes) {
			System.out.print(b + " ");
			System.out.print(Integer.toHexString(b) + " ");
			// & 0xff 是为了将b的前24位设置为0
			System.out.print(Integer.toHexString(b & 0xff) + " ");
		}

		System.out.println("\n==========================================");
		int a = 2;
		if (a <= 0xf) {
			System.out.print("0");
		}
		System.out.print(Integer.toHexString(a));

	}

}
