package com.zcy.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class RSAUtils {

	public static String getNonceStr() {
		String token = UUID.randomUUID().toString().toUpperCase();
		token = token.replace("-", "");
		return token;
	}

	public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String token = UUID.randomUUID().toString().toUpperCase();
		token = token.replace("-", "");
		System.out.println(token);
	}
}