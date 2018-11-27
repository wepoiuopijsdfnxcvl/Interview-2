package cn.collabtech.sign;

import java.util.HashMap;
import java.util.Map;

public class TestSign {
	public static void main(String[] args) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("name", "A");
		paramMap.put("password", "123456");
		String sign = SignUtil.getSign(paramMap,"key65423");
		System.out.println(sign);
	}
}
