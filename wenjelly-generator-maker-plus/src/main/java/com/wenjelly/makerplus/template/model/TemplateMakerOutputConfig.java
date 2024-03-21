package com.wenjelly.makerplus.template.model;

/*
 * @time 2024/3/21 14:36
 * @package com.wenjelly.makerplus.template.model
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import lombok.Data;

/**
 *
 */
@Data
public class TemplateMakerOutputConfig {

    // 输出规则，从未分组文件中移除组内的同名文件
    private boolean removeGroupFilesFromRoot = true;

}
