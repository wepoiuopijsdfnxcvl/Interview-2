package com.zcy.service;

import com.zcy.model.PayLog;

/**
 * @Description: 日志记录类
 */
public interface PayLogService {

	/**
	 * 生成日志表
	 * 
	 * @param tableName
	 * @throws ServiceException
	 */
	void createLogTable(String tableName) throws Exception;

	void saveLog(PayLog sysLog) throws Exception;
}
