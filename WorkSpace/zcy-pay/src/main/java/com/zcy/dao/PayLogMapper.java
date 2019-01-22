package com.zcy.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zcy.model.PayLog;

public interface PayLogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(PayLog record);

    int insertSelective(PayLog record);

    PayLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(PayLog record);

    int updateByPrimaryKeyWithBLOBs(PayLog record);

    int updateByPrimaryKey(PayLog record);

    void createTable(@Param("tableName") String tableName);

    void saveLog(@Param("params") Map<String, Object> map);
}