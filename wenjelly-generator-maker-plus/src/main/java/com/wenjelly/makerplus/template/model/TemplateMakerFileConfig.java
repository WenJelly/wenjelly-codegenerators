package com.wenjelly.makerplus.template.model;

import com.wenjelly.makerplus.meta.Meta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * @time 2024/3/17 16:01
 * @package com.wenjelly.makerplus.template.model
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */
@Data
public class TemplateMakerFileConfig {
    private List<FileInfoConfig> files;
    private FileGroupConfig fileGroupConfig;

    @Data
    @NoArgsConstructor
    public static class FileInfoConfig {
        private String path;
        private List<FileFilterConfig> filterConfigList;
    }

    @Data
    public static class FileGroupConfig {
        private String condition;
        private String groupKey;
        private String groupName;
    }
}
