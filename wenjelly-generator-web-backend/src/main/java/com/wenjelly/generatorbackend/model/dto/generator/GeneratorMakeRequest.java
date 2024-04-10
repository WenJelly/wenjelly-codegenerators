package com.wenjelly.generatorbackend.model.dto.generator;

/*
 * @time 2024/4/10 9:01
 * @package com.wenjelly.generatorbackend.model.dto.generator
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 */

import com.wenjelly.makerplus.meta.Meta;
import lombok.Data;

import java.io.Serializable;

/**
 * 制作代码生成器请求
 */
@Data
public class GeneratorMakeRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 压缩文件路径
     */
    private String zipFilePath;
    /**
     * 元信息
     */
    private Meta meta;
}
