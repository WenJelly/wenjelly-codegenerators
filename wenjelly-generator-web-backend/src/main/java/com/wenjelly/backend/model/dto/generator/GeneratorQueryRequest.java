package com.wenjelly.backend.model.dto.generator;

/*
 * @time 2024/3/24 15:00
 * @package com.wenjelly.backend.model.dto.Generator
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 * 查询请求
 */

import com.wenjelly.backend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneratorQueryRequest extends PageRequest implements Serializable {

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
    private String name;

    /**
     * 内容
     */
    private String description;

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
     * 状态
     */
    private Integer status;


    private static final long serialVersionUID = 1L;
}
