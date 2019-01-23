package com.zcy.controller;

import com.zcy.dao.UserVirtualCardMapper;
import com.zcy.model.UserVirtualCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Darrick
 * @Date: 2019/1/22 15:34
 * @Description:
 */
@RestController
public class TestController {

    @Autowired
    private UserVirtualCardMapper userVirtualCardMapper;

    @GetMapping("/test")
    public String test(){
        UserVirtualCard userVirtualCard = userVirtualCardMapper.selectByPrimaryKey(1);
        return userVirtualCard!=null?userVirtualCard.getCardBalance()+"!":"test";
    }

}
