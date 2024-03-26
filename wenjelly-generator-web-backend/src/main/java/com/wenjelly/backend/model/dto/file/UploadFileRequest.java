package com.wenjelly.backend.model.dto.file;

/*
 * @time 2024/3/24 14:59
 * @package com.wenjelly.backend.model.dto.file
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 * 文件上传请求
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadFileRequest implements Serializable {

    /**
     * 业务
     */
    private String biz;

    private static final long serialVersionUID = 1L;
}
