package com.zcy.zcmorefun.jifen.dao;

import com.zcy.zcmorefun.jifen.model.system.MerchantSetup;

public interface MerchantSetupMapper {
    int deleteByPrimaryKey(Integer setupId);

    int insert(MerchantSetup record);

    int insertSelective(MerchantSetup record);

    MerchantSetup selectByPrimaryKey(Integer setupId);

    int updateByPrimaryKeySelective(MerchantSetup record);

    int updateByPrimaryKey(MerchantSetup record);
}