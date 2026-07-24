package org.example.springblogdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.springblogdemo.common.Constants;
import org.example.springblogdemo.common.exception.BlogException;
import org.example.springblogdemo.common.util.BeanTrans;
import org.example.springblogdemo.common.util.JwtUtils;
import org.example.springblogdemo.common.util.SecurityUtils;
import org.example.springblogdemo.mapper.BlogInfoMapper;
import org.example.springblogdemo.pojo.dataobject.BlogInfo;
import org.example.springblogdemo.pojo.dataobject.UserInfo;

import org.example.springblogdemo.mapper.UserInfoMapper;
import org.example.springblogdemo.pojo.request.UserLoginRequest;
import org.example.springblogdemo.pojo.response.UserInfoResponse;
import org.example.springblogdemo.pojo.response.UserLoginResponse;
import org.example.springblogdemo.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
* @author 张童
* @description 针对表【user_info(用户表)】的数据库操作Service实现
* @createDate 2026-07-19 17:36:18
*/


import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lucf
 * @description 针对表【user_info(用户表)】的数据库操作Service实现
 * @createDate 2026-05-09 21:16:44
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private BlogInfoMapper blogInfoMapper;

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        /**
         * 1. 验证密码是否正确
         * 2. 如果正确, 生成token, 并返回
         * 3. 如果不正确, 返回错误信息
         */
        UserInfo userInfo = selectUserInfoByName(request.getUserName());
        if (userInfo==null || userInfo.getId()==null){
            throw new BlogException("用户不存在");
        }
        if (!SecurityUtils.verify(
                request.getPassword(),
                userInfo.getPassword())){
            throw new BlogException("密码不正确");
        }
        //账号密码正确F
        Map<String, Object> claim = new HashMap<>();
        claim.put("id", userInfo.getId());
        claim.put("name", userInfo.getUserName());

        String token = JwtUtils.genJwt(claim);
        return new UserLoginResponse(userInfo.getId(), token);
    }

    @Override
    public UserInfoResponse getAuthorInfo(Integer blogId) {
        //根据博客id, 获取作者id
        BlogInfo blogInfo = getBlogInfoById(blogId);
        if (blogInfo==null){
            throw new BlogException("博客不存在");
        }
        return getUserInfo(blogInfo.getUserId());
    }

    @Override
    public UserInfoResponse getUserInfo(Integer userId) {
        UserInfo userInfo = getUserInfoById(userId);
        if (userInfo==null){
            throw new BlogException("用户不存在");
        }
        return BeanTrans.trans(userInfo);
    }

    /**
     * 根据userId 获取用户信息
     * @param userId 用户ID
     * @return
     */
    public UserInfo getUserInfoById(Integer userId){
        LambdaQueryWrapper wrapper =
                new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getId, userId)
                .eq(UserInfo::getDeleteFlag, Constants.NOT_DELETE);
        return userInfoMapper.selectOne(wrapper);
    }

    private UserInfo selectUserInfoByName(String userName) {
        LambdaQueryWrapper wrapper = new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUserName, userName)
                .eq(UserInfo::getDeleteFlag, Constants.NOT_DELETE);
        return userInfoMapper.selectOne(wrapper);
    }

    /**
     * 根据blogId, 获取博客信息
     * @param blogId  博客ID
     * @return
     */
    public BlogInfo getBlogInfoById(Integer blogId){
        BlogInfo blogInfo = blogInfoMapper.selectOne(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getId, blogId).eq(BlogInfo::getDeleteFlag, Constants.NOT_DELETE));
        return blogInfo;
    }

}









