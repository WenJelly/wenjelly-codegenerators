package com.wenjelly.springboottemplate.model.dto.file;

/*
 * @time 2024/3/24 14:59
 * @package com.wenjelly.springboottemplate.model.dto.file
 * @project spring-boot-template
 * @author WenJelly
 * 文件上传请求
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadFileRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 业务
     */
    private String biz;
}
