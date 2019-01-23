package com.zcy.test.dao;

import com.zcy.ZcyPayApplication;
import com.zcy.dao.UserVirtualCardMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Darrick
 * @Date: 2019/1/22 14:05
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZcyPayApplication.class)
@EnableAutoConfiguration
public class DBTest {

    @Autowired
    private UserVirtualCardMapper userVirtualCardMapper;

    @Test
    public void test() {
        System.out.println(userVirtualCardMapper.selectByPrimaryKey(1));
    }


}
