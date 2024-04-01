package com.wenjelly.springboottemplate.common;

/*
 * @time 2024/3/24 14:12
 * @package com.wenjelly.springboottemplate.common
 * @project spring-boot-template
 * @author WenJelly
 * 删除请求
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
