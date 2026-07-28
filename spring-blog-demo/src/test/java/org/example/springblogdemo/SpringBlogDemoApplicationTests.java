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
        //需要处理的用户id
        int[] ids = {1, 2};

        for (int id : ids) {
            UserInfo userInfo = userInfoMapper.selectById(id);
            if (userInfo == null) {
                continue;
            }
            //假设原密码都是123456
            String password = "123456";
            String encryptPassword = SecurityUtils.encrypt(password);
            userInfo.setPassword(encryptPassword);
            userInfoMapper.updateById(userInfo);
        }
    }
}
