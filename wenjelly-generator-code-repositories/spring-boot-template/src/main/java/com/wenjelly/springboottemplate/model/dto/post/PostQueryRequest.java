package com.wenjelly.springboottemplate.model.dto.post;

/*
 * @time 2024/3/24 15:00
 * @package com.wenjelly.springboottemplate.model.dto.post
 * @project spring-boot-template
 * @author WenJelly
 * 查询请求
 */

import com.wenjelly.springboottemplate.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * id
     */
    private Long notId;
    /**
     * 搜索词
     */
    private String searchText;
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
    /**
     * 至少有一个标签
     */
    private List<String> orTags;
    /**
     * 创建用户 id
     */
    private Long userId;
    /**
     * 收藏用户 id
     */
    private Long favourUserId;
}
