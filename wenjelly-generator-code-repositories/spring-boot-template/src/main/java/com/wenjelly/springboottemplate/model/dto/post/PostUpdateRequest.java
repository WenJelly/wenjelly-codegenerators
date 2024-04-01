package com.wenjelly.springboottemplate.model.dto.post;

/*
 * @time 2024/3/24 15:00
 * @package com.wenjelly.springboottemplate.model.dto.post
 * @project spring-boot-template
 * @author WenJelly
 * 更新请求
 */

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PostUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    private static final long serialVersionUID = 1L;
}
