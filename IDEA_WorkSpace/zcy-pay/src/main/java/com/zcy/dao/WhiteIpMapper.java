package com.zcy.dao;

import java.util.List;

import com.zcy.model.WhiteIp;

public interface WhiteIpMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(WhiteIp record);

	int insertSelective(WhiteIp record);

	WhiteIp selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(WhiteIp record);

	int updateByPrimaryKey(WhiteIp record);

	List<WhiteIp> selectWhiteIpList();
}