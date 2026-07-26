package org.example.springblogdemo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.constraints.NotNull;
import org.example.springblogdemo.pojo.dataobject.BlogInfo;
import org.example.springblogdemo.pojo.request.AddBlogInfoRequest;
import org.example.springblogdemo.pojo.request.UpBlogRequest;
import org.example.springblogdemo.pojo.response.BlogInfoResponse;

import java.util.List;


//IService 是 MyBatis-Plus 提供的接口
//里面已经定义好了很多通用数据库操作
public interface BlogInfoService extends IService<BlogInfo> {

    //通用 CRUD 不够，自己增加查询博客列表
    List<BlogInfoResponse> getListByDesc();

    //查看博客详情(查看)
    BlogInfoResponse getBlogDetail(Integer blogId);

    //发布博客(发布)
    Boolean addBlog(AddBlogInfoRequest addBlogInfoRequest);

    //修改博客(编辑)
    Boolean updateBlog(UpBlogRequest request);

    //删除
    Boolean delete(@NotNull(message = "博客ID不能为空") Integer blogId);
}
