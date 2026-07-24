package org.example.springblogdemo;

import org.example.springblogdemo.common.util.SecurityUtils;
import org.example.springblogdemo.mapper.UserInfoMapper;
import org.example.springblogdemo.pojo.dataobject.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBlogDemoApplicationTests {

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Test
    void updatePassword() {
        // 原密码
        String password = "123456";
        // 加密
        String encryptPassword = SecurityUtils.encrypt(password);
        System.out.println(encryptPassword);
        // 查询用户（假设id=1）
        UserInfo userInfo = userInfoMapper.selectById(1);
        // 修改密码
        userInfo.setPassword(encryptPassword);
        // 更新数据库
        userInfoMapper.updateById(userInfo);
    }

}
