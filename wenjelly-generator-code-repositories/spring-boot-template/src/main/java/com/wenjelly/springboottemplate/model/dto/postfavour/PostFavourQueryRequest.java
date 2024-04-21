package com.wenjelly.springboottemplate.model.dto.postfavour;

/*
 * @time 2024/3/24 15:01
 * @package com.wenjelly.springboottemplate.model.dto.postfavour
 * @project spring-boot-template
 * @author WenJelly
 * 帖子收藏查询请求
 */

import com.wenjelly.springboottemplate.common.PageRequest;
import com.wenjelly.springboottemplate.model.dto.post.PostQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostFavourQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 帖子查询请求
     */
    private PostQueryRequest postQueryRequest;
    /**
     * 用户 id
     */
    private Long userId;
}