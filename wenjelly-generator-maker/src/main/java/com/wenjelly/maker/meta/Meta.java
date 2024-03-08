package com.wenjelly.maker.meta;

/*
 * @time 2024/3/8 10:50
 * @package com.wenjelly.maker.meta
 * @project wenjelly-generator-maker
 * @author WenJelly
 */

import java.util.List;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public FileConfigBean getFileConfig() {
        return fileConfig;
    }

    public void setFileConfig(FileConfigBean fileConfig) {
        this.fileConfig = fileConfig;
    }

    public ModelConfigBean getModelConfig() {
        return modelConfig;
    }

    public void setModelConfig(ModelConfigBean modelConfig) {
        this.modelConfig = modelConfig;
    }

    public static class FileConfigBean {
        /**
         * inputRootPath : D:/Development/IDEAJavaProjects/wenjelly-generators/wenjelly-generator-demo-projects/acm-template
         * outputRootPath : generated
         * type : dir
         * files : [{"inputPath":"src/com/wenjelly/acm/MainTemplate.java.ftl","outputPath":"src/com/wenjelly/acm/MainTemplate.java","type":"file","generateType":"dynamic"},{"inputPath":".gitignore","outputPath":".gitignore","type":"file","generateType":"static"},{"inputPath":"README.md","outputPath":"README.md","type":"file","generateType":"static"}]
         */

        private String inputRootPath;
        private String outputRootPath;
        private String type;
        private List<FileInfo> files;

        public String getInputRootPath() {
            return inputRootPath;
        }

        public void setInputRootPath(String inputRootPath) {
            this.inputRootPath = inputRootPath;
        }

        public String getOutputRootPath() {
            return outputRootPath;
        }

        public void setOutputRootPath(String outputRootPath) {
            this.outputRootPath = outputRootPath;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<FileInfo> getFiles() {
            return files;
        }

        public void setFiles(List<FileInfo> files) {
            this.files = files;
        }

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

            public String getInputPath() {
                return inputPath;
            }

            public void setInputPath(String inputPath) {
                this.inputPath = inputPath;
            }

            public String getOutputPath() {
                return outputPath;
            }

            public void setOutputPath(String outputPath) {
                this.outputPath = outputPath;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getGenerateType() {
                return generateType;
            }

            public void setGenerateType(String generateType) {
                this.generateType = generateType;
            }

            @Override
            public String toString() {
                return "FileInfo{" +
                        "inputPath='" + inputPath + '\'' +
                        ", outputPath='" + outputPath + '\'' +
                        ", type='" + type + '\'' +
                        ", generateType='" + generateType + '\'' +
                        '}';
            }
        }
    }

    public static class ModelConfigBean {
        private List<ModelInfo> models;

        public List<ModelInfo> getModels() {
            return models;
        }

        public void setModels(List<ModelInfo> models) {
            this.models = models;
        }

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

            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }


            public String getAbbr() {
                return abbr;
            }

            public void setAbbr(String abbr) {
                this.abbr = abbr;
            }

            @Override
            public String toString() {
                return "ModelInfo{" +
                        "fieldName='" + fieldName + '\'' +
                        ", type='" + type + '\'' +
                        ", description='" + description + '\'' +
                        ", defaultValue=" + defaultValue +
                        ", abbr='" + abbr + '\'' +
                        '}';
            }

            public Object getDefaultValue() {
                return defaultValue;
            }

            public void setDefaultValue(Object defaultValue) {
                this.defaultValue = defaultValue;
            }
        }
    }

    @Override
    public String toString() {
        return "Meta{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", basePackage='" + basePackage + '\'' +
                ", version='" + version + '\'' +
                ", author='" + author + '\'' +
                ", createTime='" + createTime + '\'' +
                ", fileConfig=" + fileConfig +
                ", modelConfig=" + modelConfig +
                '}';
    }
}
