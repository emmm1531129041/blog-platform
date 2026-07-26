package org.example.springblogdemo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.constraints.NotNull;
import org.example.springblogdemo.pojo.dataobject.UserInfo;
import org.example.springblogdemo.pojo.request.UserLoginRequest;
import org.example.springblogdemo.pojo.response.UserInfoResponse;
import org.example.springblogdemo.pojo.response.UserLoginResponse;


public interface UserInfoService extends IService<UserInfo> {
    //登录
    UserLoginResponse login(UserLoginRequest request);

    //获取用户信息
    UserInfoResponse getUserInfo(@NotNull Integer userId);

    //获取作者信息
    UserInfoResponse getAuthorInfo(@NotNull Integer blogId);
}

