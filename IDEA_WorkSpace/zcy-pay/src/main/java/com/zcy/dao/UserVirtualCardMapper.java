package com.zcy.dao;

import com.zcy.model.UserVirtualCard;

public interface UserVirtualCardMapper {
    int deleteByPrimaryKey(Integer virtualCardId);

    int insert(UserVirtualCard record);

    int insertSelective(UserVirtualCard record);

    UserVirtualCard selectByPrimaryKey(Integer virtualCardId);

    int updateByPrimaryKeySelective(UserVirtualCard record);

    int updateByPrimaryKey(UserVirtualCard record);
}