package com.zcy.service.impl;

import com.zcy.dao.PayLogMapper;
import com.zcy.model.PayLog;
import com.zcy.service.PayLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 日志记录类
 */
@Service
public class PayLogServiceImpl implements PayLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayLogServiceImpl.class);

	@Autowired
	private PayLogMapper payLogMapper;

	/**
	 * 创建表
	 * 
	 * @param tableName
	 * @throws ServiceException
	 */
	@Override
	public void createLogTable(String tableName) throws Exception {
		try {
			payLogMapper.createTable(tableName);
		} catch (Exception e) {
			LOGGER.error(" createLogTable fail：" + e);
		}
	}

	/**
	 * 保存日志
	 * 
	 * @param sysLog
	 * @throws ServiceException
	 */
	@Override
	public void saveLog(PayLog sysLog) {
		try {
			// 获取当前日志表名
			Map parmMap = new HashMap();
			parmMap.put("tableName", "pay_log");
			parmMap.put("sysLog", sysLog);
			payLogMapper.saveLog(parmMap);
		} catch (Exception e) {
			LOGGER.error(" saveLog fail：" + e);
		}
	}
}
