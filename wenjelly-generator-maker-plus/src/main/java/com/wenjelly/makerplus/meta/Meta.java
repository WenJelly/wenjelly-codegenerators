package com.wenjelly.makerplus.meta;

/*
 * @time 2024/3/8 10:50
 * @package com.wenjelly.makerplus.meta
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 元信息定义
 */
@Data
public class Meta implements Serializable {
    private String name;
    private String description;
    private String basePackage;
    private String version;
    private String author;
    private String createTime;
    private FileConfigBean fileConfig;
    private ModelConfigBean modelConfig;

    @Data
    public static class FileConfigBean implements Serializable {
        private String inputRootPath;
        private String outputRootPath;
        private String sourceRootPath;
        private String type;
        private List<FileInfo> files;

        @Data
        public static class FileInfo implements Serializable {
            private String inputPath;
            private String outputPath;
            private String type;
            private String generateType;
            private String groupKey;
            private String groupName;
            private String condition;
            private List<FileInfo> files;
        }
    }

    @Data
    public static class ModelConfigBean implements Serializable {
        private List<ModelInfo> models;

        @Data
        public static class ModelInfo implements Serializable {
            private String fieldName;
            private String type;
            private String description;
            private Object defaultValue;
            private String abbr;
            private String groupKey;
            private String groupName;
            private List<ModelInfo> models;
            private String condition;
            // 中间参数
            // 该分组下所有的参数拼接字符串
            private String allArgsStr;
        }
    }
}
