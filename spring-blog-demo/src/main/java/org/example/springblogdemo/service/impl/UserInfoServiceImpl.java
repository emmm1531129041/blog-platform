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
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;


//ServiceImpl这个是MyBatis-Plus提供的
//它已经帮忙写好了很多CRUD
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private BlogInfoMapper blogInfoMapper;

    //登录
    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        //在数据库中找到用户,此处调用下面 selectUserInfoByName
        UserInfo userInfo = selectUserInfoByName(request.getUserName());
        if (userInfo==null || userInfo.getId()==null){
            throw new BlogException("用户不存在");
        }
        //验证密码
        if (!SecurityUtils.verify(
                request.getPassword(),
                userInfo.getPassword())){
            throw new BlogException("密码不正确");
        }
        //账号密码正确,存入一个对象
        Map<String, Object> claim = new HashMap<>();
        claim.put("id", userInfo.getId());
        claim.put("name", userInfo.getUserName());

        //生成JWT
        //并返回用户能看的信息
        String token = JwtUtils.genJwt(claim);
        return new UserLoginResponse(userInfo.getId(), token);
    }

    //登录辅助方法
    private UserInfo selectUserInfoByName(String userName) {
        LambdaQueryWrapper wrapper = new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUserName, userName)
                .eq(UserInfo::getDeleteFlag, Constants.NOT_DELETE);
        return userInfoMapper.selectOne(wrapper);
    }

    //获取作者信息
    @Override
    public UserInfoResponse getAuthorInfo(Integer blogId) {
        //根据博客id, 获取作者id
        BlogInfo blogInfo = getBlogInfoById(blogId);
        if (blogInfo==null){
            throw new BlogException("博客不存在");
        }
        return getUserInfo(blogInfo.getUserId());
    }

    //获取用户信息
    @Override
    public UserInfoResponse getUserInfo(Integer userId) {
        UserInfo userInfo = getUserInfoById(userId);
        if (userInfo==null){
            throw new BlogException("用户不存在");
        }
        return BeanTrans.trans(userInfo);
    }


    //根据用户id查用户完整信息
    public UserInfo getUserInfoById(Integer userId){
        LambdaQueryWrapper wrapper =
                new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getId, userId)
                .eq(UserInfo::getDeleteFlag, Constants.NOT_DELETE);
        return userInfoMapper.selectOne(wrapper);
    }

    //根据blogId, 获取博客信息
    public BlogInfo getBlogInfoById(Integer blogId){
        BlogInfo blogInfo = blogInfoMapper.selectOne(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getId, blogId).eq(BlogInfo::getDeleteFlag, Constants.NOT_DELETE));
        return blogInfo;
    }

}









