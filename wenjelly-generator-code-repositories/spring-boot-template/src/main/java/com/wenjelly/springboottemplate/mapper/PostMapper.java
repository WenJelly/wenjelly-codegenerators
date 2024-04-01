package com.wenjelly.springboottemplate.mapper;

/*
 * @time 2024/3/24 14:48
 * @package com.wenjelly.springboottemplate.mapper
 * @project spring-boot-template
 * @author WenJelly
 * 帖子数据库操作
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wenjelly.springboottemplate.model.entity.Post;

import java.util.Date;
import java.util.List;

public interface PostMapper extends BaseMapper<Post> {

    /**
     * 查询帖子列表（包括已被删除的数据）
     */
    List<Post> listPostWithDelete(Date minUpdateTime);

}
