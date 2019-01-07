package com.zcy.zcmorefun.jifen.dao;

import com.zcy.zcmorefun.jifen.model.system.ActivityKey;

public interface ActivityKeyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityKey record);

    int insertSelective(ActivityKey record);

    ActivityKey selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityKey record);

    int updateByPrimaryKey(ActivityKey record);
}