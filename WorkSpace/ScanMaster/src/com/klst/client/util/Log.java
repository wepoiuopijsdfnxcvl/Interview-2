package com.klst.client.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

	public static Logger getLogger(String name){
		return LogManager.getLogger(name);
	}

}
