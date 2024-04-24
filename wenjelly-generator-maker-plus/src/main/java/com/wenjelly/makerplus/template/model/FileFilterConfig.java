package com.wenjelly.makerplus.template.model;

/*
 * @time 2024/3/17 16:00
 * @package com.wenjelly.makerplus.template.model
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import lombok.Builder;
import lombok.Data;

/**
 * 文件过滤规则配置
 */
@Data
@Builder
public class FileFilterConfig {

    private String range;
    private String rule;
    private String value;

}
