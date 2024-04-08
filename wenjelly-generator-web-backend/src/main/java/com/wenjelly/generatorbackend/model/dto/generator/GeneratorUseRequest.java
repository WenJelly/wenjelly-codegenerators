package com.wenjelly.generatorbackend.model.dto.generator;

/*
 * @time 2024/4/3 9:49
 * @package com.wenjelly.generatorbackend.model.dto.generator
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 */

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 使用代码生成器请求
 */
@Data
public class GeneratorUseRequest implements Serializable {

    /**
     * 生成器id
     */
    private Long id;

    /**
     * 生成器的模型参数
     */
    Map<String, Object> dataModel;

    private static final long serialVersionUID = 1L;

}
