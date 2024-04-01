package com.wenjelly.springboottemplate.model.dto.postthumb;

/*
 * @time 2024/3/24 15:01
 * @package com.wenjelly.springboottemplate.model.dto.postthumb
 * @project spring-boot-template
 * @author WenJelly
 * 帖子点赞请求
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class PostThumbAddRequest implements Serializable {

    /**
     * 帖子 id
     */
    private Long postId;

    private static final long serialVersionUID = 1L;
}
