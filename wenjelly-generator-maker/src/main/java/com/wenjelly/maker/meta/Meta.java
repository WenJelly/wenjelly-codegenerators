package com.wenjelly.maker.meta;

/*
 * @time 2024/3/8 10:50
 * @package com.wenjelly.maker.meta
 * @project wenjelly-generator-maker
 * @author WenJelly
 */

import lombok.Data;

import java.util.List;

@Data
public class Meta {
    /**
     * name : acm-template-generator
     * description : ACM 示例模板生成器
     * basePackage : com.wenjelly
     * version : 1.0
     * author : wenjelly
     * createTime : 2024-3-8
     * fileConfig : {"inputRootPath":"D:/Development/IDEAJavaProjects/wenjelly-generators/wenjelly-generator-demo-projects/acm-template","outputRootPath":"generated","type":"dir","files":[{"inputPath":"src/com/wenjelly/acm/MainTemplate.java.ftl","outputPath":"src/com/wenjelly/acm/MainTemplate.java","type":"file","generateType":"dynamic"},{"inputPath":".gitignore","outputPath":".gitignore","type":"file","generateType":"static"},{"inputPath":"README.md","outputPath":"README.md","type":"file","generateType":"static"}]}
     * modelConfig : {"models":[{"fieldName":"loop","type":"boolean","description":"是否生成循环","defaultValue":false,"abbr":"l"},{"fieldName":"author","type":"String","description":"作者注释","defaultValue":"WenJelly","abbr":"a"},{"fieldName":"outputText","type":"String","description":"输出信息","defaultValue":"sum = ","abbr":"o"}]}
     */

    private String name;
    private String description;
    private String basePackage;
    private String version;
    private String author;
    private String createTime;
    private FileConfigBean fileConfig;
    private ModelConfigBean modelConfig;

    @Data
    public static class FileConfigBean {
        /**
         * inputRootPath : D:/Development/IDEAJavaProjects/wenjelly-generators/wenjelly-generator-demo-projects/acm-template
         * outputRootPath : generated
         * type : dir
         * files : [{"inputPath":"src/com/wenjelly/acm/MainTemplate.java.ftl","outputPath":"src/com/wenjelly/acm/MainTemplate.java","type":"file","generateType":"dynamic"},{"inputPath":".gitignore","outputPath":".gitignore","type":"file","generateType":"static"},{"inputPath":"README.md","outputPath":"README.md","type":"file","generateType":"static"}]
         */

        private String inputRootPath;
        private String outputRootPath;
        private String sourceRootPath;
        private String type;
        private List<FileInfo> files;

        @Data
        public static class FileInfo {
            /**
             * inputPath : src/com/wenjelly/acm/MainTemplate.java.ftl
             * outputPath : src/com/wenjelly/acm/MainTemplate.java
             * type : file
             * generateType : dynamic
             */

            private String inputPath;
            private String outputPath;
            private String type;
            private String generateType;

        }
    }

    @Data
    public static class ModelConfigBean {
        private List<ModelInfo> models;

        @Data
        public static class ModelInfo {
            /**
             * fieldName : loop
             * type : boolean
             * description : 是否生成循环
             * defaultValue : false
             * abbr : l
             */

            private String fieldName;
            private String type;
            private String description;
            private Object defaultValue;
            private String abbr;
        }
    }
}
