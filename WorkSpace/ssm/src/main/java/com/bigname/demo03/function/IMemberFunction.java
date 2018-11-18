package com.bigname.demo03.function;

import com.bigname.demo03.core.Member;

public interface IMemberFunction {
	Member login(String name, String passsword) throws Exception;
}
