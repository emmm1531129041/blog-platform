package org.example.springblogdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.springblogdemo.pojo.dataobject.BlogInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 张童
* @description 针对表【blog_info(博客表)】的数据库操作Mapper
* @createDate 2026-07-19 17:36:18
* @Entity org.example.springblogdemo.pojo.dataobject.BlogInfo
*/
//为什么需要泛型 BlogInfo?
//这个Mapper操作的是BlogInfo这张表
@Mapper
public interface BlogInfoMapper extends BaseMapper<BlogInfo> {

}




