package com.wenjelly.springboottemplate.common;

/*
 * @time 2024/3/24 14:12
 * @package com.wenjelly.springboottemplate.common
 * @project spring-boot-template
 * @author WenJelly
 * 分页请求
 */

import com.wenjelly.springboottemplate.constant.CommonConstant;
import lombok.Data;

@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
