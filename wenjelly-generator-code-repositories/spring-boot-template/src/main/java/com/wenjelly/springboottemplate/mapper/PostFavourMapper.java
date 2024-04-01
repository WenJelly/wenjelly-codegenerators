package com.wenjelly.springboottemplate.mapper;

/*
 * @time 2024/3/24 14:48
 * @package com.wenjelly.springboottemplate.mapper
 * @project spring-boot-template
 * @author WenJelly
 * 帖子收藏数据库操作
 */

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenjelly.springboottemplate.model.entity.Post;
import com.wenjelly.springboottemplate.model.entity.PostFavour;
import org.apache.ibatis.annotations.Param;

public interface PostFavourMapper extends BaseMapper<PostFavour> {

    /**
     * 分页查询收藏帖子列表
     *
     * @param page
     * @param queryWrapper
     * @param favourUserId
     * @return
     */
    Page<Post> listFavourPostByPage(IPage<Post> page, @Param(Constants.WRAPPER) Wrapper<Post> queryWrapper,
                                    long favourUserId);

}
