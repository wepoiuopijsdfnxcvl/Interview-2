package test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NumberTest {

	/**
	 * 通兑订单编号规则 （） 商户id后4位 (不足4位补0)
	 * 
	 * ZC+4+190117+4位随机数 （17）
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddSSS");
		String newDate = sdf.format(new Date());
		int random = RandomUtils.random(4);
		
		String merchantId01 = 1+"";
		String merchantId03 = 333+"";
		String merchantId04 = 4444+"";
		String merchantId05 = 55555+"";
		
		String merchantStr = merchantId05;
		StringBuilder sb = new StringBuilder();
		if(merchantStr.length()>4) {
			merchantStr = merchantStr.substring(merchantStr.length()-4, merchantStr.length());
		}else if(merchantStr.length()<4){
		    while(merchantStr.length()<4) {
		    	merchantStr="0"+merchantStr;
		    }
		}
		System.out.println("ZC"+merchantStr+newDate + random);
	}

}
