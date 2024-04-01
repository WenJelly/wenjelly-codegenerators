package com.wenjelly.springboottemplate.model.dto.postfavour;

/*
 * @time 2024/3/24 15:01
 * @package com.wenjelly.springboottemplate.model.dto.postfavour
 * @project spring-boot-template
 * @author WenJelly
 * 帖子收藏 / 取消收藏请求
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class PostFavourAddRequest implements Serializable {

    /**
     * 帖子 id
     */
    private Long postId;

    private static final long serialVersionUID = 1L;
}
