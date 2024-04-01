package com.wenjelly.springboottemplate.service;

/*
 * @time 2024/3/24 14:56
 * @package com.wenjelly.springboottemplate.service
 * @project spring-boot-template
 * @author WenJelly
 * 帖子点赞服务
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.wenjelly.springboottemplate.model.entity.PostThumb;
import com.wenjelly.springboottemplate.model.entity.User;

public interface PostThumbService extends IService<PostThumb> {

    /**
     * 点赞
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostThumb(long postId, User loginUser);

    /**
     * 帖子点赞（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostThumbInner(long userId, long postId);
}