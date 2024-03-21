package com.wenjelly.makerplus.template.model;

/*
 * @time 2024/3/20 19:56
 * @package com.wenjelly.makerplus.template.model
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */


import com.wenjelly.makerplus.meta.Meta;
import lombok.Data;

/**
 * 模板制作配置
 */
@Data
public class TemplateMakerConfig {
    private Long id;
    private Meta newMeta;
    private TemplateMakerModelConfig templateMakerModelConfig =new TemplateMakerModelConfig();
    private TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();
    private TemplateMakerOutputConfig templateMakerOutputConfig = new TemplateMakerOutputConfig();
    private String originProjectPath;
}
