package com.wenjelly.generatorbackend.model.dto.generator;

/*
 * @time 2024/4/11 17:00
 * @package com.wenjelly.generatorbackend.model.dto.generator
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class GeneratorCacheRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 生成器的id
     */
    private Long id;
    /**
     * 生成器在对象存储的地址
     */
    private String distPath;

}
