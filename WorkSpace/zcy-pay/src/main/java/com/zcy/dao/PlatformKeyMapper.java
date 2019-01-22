package com.zcy.dao;

import com.zcy.model.PlatformKey;

public interface PlatformKeyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlatformKey record);

    int insertSelective(PlatformKey record);

    PlatformKey selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlatformKey record);

    int updateByPrimaryKey(PlatformKey record);

    PlatformKey selectSecretKeyByApiKey(String apiKey);
}