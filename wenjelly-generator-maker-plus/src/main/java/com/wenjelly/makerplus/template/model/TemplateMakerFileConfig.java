package com.wenjelly.makerplus.template.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * @time 2024/3/17 16:01
 * @package com.wenjelly.makerplus.template.model
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

/**
 * 模板文件配置（文件的输入输出位置等）
 */
@Data
public class TemplateMakerFileConfig {


    private List<FileInfoConfig> files;
    private FileGroupConfig fileGroupConfig;

    @Data
    @NoArgsConstructor
    public static class FileInfoConfig {
        private String path;
        private String condition;
        private List<FileFilterConfig> filterConfigList;
    }

    @Data
    public static class FileGroupConfig {
        private String condition;
        private String groupKey;
        private String groupName;
    }
}
