package org.example.springblogdemo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.springblogdemo.common.Constants;
import org.example.springblogdemo.common.exception.BlogException;
import org.example.springblogdemo.common.util.BeanTrans;
import org.example.springblogdemo.mapper.UserInfoMapper;
import org.example.springblogdemo.pojo.dataobject.BlogInfo;
import org.example.springblogdemo.mapper.BlogInfoMapper;
import org.example.springblogdemo.pojo.request.AddBlogInfoRequest;
import org.example.springblogdemo.pojo.request.UpBlogRequest;
import org.example.springblogdemo.pojo.response.BlogInfoResponse;
import org.example.springblogdemo.service.BlogInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


//ServiceImpl这个是MyBatis-Plus提供的
//它已经帮忙写好了很多CRUD
@Service
public class BlogInfoServiceImpl extends ServiceImpl<BlogInfoMapper, BlogInfo>
        implements BlogInfoService {
    @Autowired
    private BlogInfoMapper blogInfoMapper;

    //查询博客列表的具体实现
    @Override
    public List<BlogInfoResponse> getListByDesc() {
        //查询 blog_info 表里面所有没有被删除的博客，并且按照 id 降序排列
        List<BlogInfo> blogInfos = blogInfoMapper.selectList(
                new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getDeleteFlag, Constants.NOT_DELETE)
                .orderByDesc(BlogInfo::getId)
        );
        //批量转换BlogInfo类型为BlogInfoResponse类型
        List<BlogInfoResponse> blogInfoResponses = blogInfos.stream()
                .map(blogInfo-> BeanTrans.trans(blogInfo))
                .toList();
        return blogInfoResponses;
    }

    @Override
    public BlogInfoResponse getBlogDetail(Integer blogId) {
        BlogInfo blogInfo = blogInfoMapper.selectOne(
                new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getId, blogId)
                .eq(BlogInfo::getDeleteFlag, Constants.NOT_DELETE)
        );
        if (blogInfo==null){
            throw new BlogException("博客不存在");
        }
        return BeanTrans.trans(blogInfo);
    }

    //发布博客
    @Override
    public Boolean addBlog(AddBlogInfoRequest addBlogInfoRequest) {
        BlogInfo blogInfo = BeanTrans.trans(addBlogInfoRequest);

        try {
            blogInfoMapper.insert(blogInfo);
            return true;
        }catch (Exception e){
            log.error("发布博客失败, e:{}", e);
            return false;
        }
    }

    @Override
    public Boolean updateBlog(UpBlogRequest request) {
        BlogInfo blogInfo = BeanTrans.trans(request);
        try {
            Integer result = blogInfoMapper.updateById(blogInfo);
            return result==1?true:false;
        }catch (Exception e){
            log.error("更新博客失败, e:", e);
            return false;
        }
    }

    //执行逻辑删除
    @Override
    public Boolean delete(Integer blogId) {
        BlogInfo blogInfo = new BlogInfo();
        blogInfo.setId(blogId);
        blogInfo.setDeleteFlag(Constants.IS_DELETE);
        try {
            Integer result = blogInfoMapper.updateById(blogInfo);
            return result==1?true:false;
        }catch (Exception e){
            log.error("删除博客失败, e:", e);
            return false;
        }
    }

}









