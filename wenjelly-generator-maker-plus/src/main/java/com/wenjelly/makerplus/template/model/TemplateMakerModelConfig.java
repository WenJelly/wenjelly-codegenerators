package com.wenjelly.makerplus.template.model;

/*
 * @time 2024/3/18 22:05
 * @package com.wenjelly.makerplus.template.model
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import com.wenjelly.makerplus.meta.Meta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class TemplateMakerModelConfig {

    private List<ModelInfoConfig> models;
    private ModelGroupConfig modelGroupConfig;


    @Data
    @NoArgsConstructor
    public static class ModelInfoConfig {

        private String fieldName;
        private String type;
        private String description;
        private Object defaultValue;
        private String abbr;

        // 用于替换哪些文本
        private String replaceText;

    }

    @Data
    public static class ModelGroupConfig {

        private String groupKey;
        private String groupName;
        private String condition;

    }

}
