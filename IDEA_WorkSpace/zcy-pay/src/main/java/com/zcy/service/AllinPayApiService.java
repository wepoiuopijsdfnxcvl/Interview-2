package com.zcy.service;

import java.util.Map;
import java.util.TreeMap;

import com.zcy.vo.CardAddResponse;
import com.zcy.vo.CardBackResponse;
import com.zcy.vo.CardRegisterResponse;

/**
 **
 * @author breeze
 * @date 2019-01-22 10:21
 * @Description 通联api接口业务类
 */
public interface AllinPayApiService {

	/**
	 * 客户注册（虚拟卡）
	 * 
	 * @param queryMap
	 * @return
	 */
	CardRegisterResponse cardRegister(TreeMap<String, String> queryMap);

	/**
	 * 虚拟卡充值
	 * 
	 * @param queryMap
	 * @return
	 */
	CardAddResponse cardAdd(TreeMap<String, String> queryMap);

	/**
	 * 虚拟卡退卡
	 * 
	 * @param queryMap
	 * @return
	 */
	CardBackResponse cardBack(TreeMap<String, String> queryMap);
}
