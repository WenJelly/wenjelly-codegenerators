/*
 * @time 2024/3/7 12:17
 * @package PACKAGE_NAME
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.wenjelly.makerplus.meta.Meta;
import com.wenjelly.makerplus.meta.enums.FileGenerateTypeEnum;
import com.wenjelly.makerplus.meta.enums.FileTypeEnum;
import com.wenjelly.makerplus.template.enums.FileFilterRangeEnum;
import com.wenjelly.makerplus.template.enums.FileFilterRuleEnum;
import com.wenjelly.makerplus.template.model.FileFilterConfig;
import com.wenjelly.makerplus.template.model.TemplateMakerConfig;
import com.wenjelly.makerplus.template.model.TemplateMakerFileConfig;
import com.wenjelly.makerplus.template.model.TemplateMakerModelConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        // 指定项目原始路径
        String property = System.getProperty("user.dir");
        // 制作一个文件模板
//        String originProjectPath = new File(property).getParent() + File.separator + "wenjelly-generator-code-repositories/acm-template";
        // 制作一整个目录模板
        String originProjectPath = new File(property).getParent() + File.separator + "wenjelly-generator-maker-plus/src";

        Meta newMeta = new Meta();
        // 一、输入信息
        // 1.基本文件信息
        String name = "acm-template";
        String description = "ACM示例模板生成器";
        String version = "1.0";
        String author = "wenjelly";

        newMeta.setName(name);
        newMeta.setDescription(description);
        newMeta.setVersion(version);
        newMeta.setAuthor(author);


        // 输入路径
        String fileInputPath = "main";
        ArrayList<String> fileInputPathList = new ArrayList<>();
        fileInputPathList.add(fileInputPath);

        // 3.输入模型参数信息（新信息）
        Meta.ModelConfigBean.ModelInfo modelInfo = new Meta.ModelConfigBean.ModelInfo();
        modelInfo.setFieldName("outputText");
        modelInfo.setType("String");
        modelInfo.setDescription("作者");
        modelInfo.setDefaultValue("Sum: ");

        // 4.输入文件参数信息（新信息）
        Meta.FileConfigBean.FileInfo fileInfo = new Meta.FileConfigBean.FileInfo();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());

        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();
        ArrayList<TemplateMakerFileConfig.FileInfoConfig> fileInfoConfigs = new ArrayList<>();
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig.setPath(fileInputPath);
        FileFilterConfig fileFilterConfig = FileFilterConfig.builder()
                .range(FileFilterRangeEnum.FILE_NAME.getValue())
                .rule(FileFilterRuleEnum.CONTAINS.getValue())
                .value("Command").build();
        ArrayList<FileFilterConfig> fileFilterConfigs = new ArrayList<>();
        fileFilterConfigs.add(fileFilterConfig);
        fileInfoConfig.setFilterConfigList(fileFilterConfigs);
        fileInfoConfigs.add(fileInfoConfig);
        templateMakerFileConfig.setFiles(fileInfoConfigs);

        // 设置文件组
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = new TemplateMakerFileConfig.FileGroupConfig();
        fileGroupConfig.setCondition("test");
        fileGroupConfig.setGroupKey("output");
        fileGroupConfig.setGroupName("测试分组");

        templateMakerFileConfig.setFileGroupConfig(fileGroupConfig);

        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        // - 模型组配置
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = new TemplateMakerModelConfig.ModelGroupConfig();
        modelGroupConfig.setGroupKey("mysql");
        modelGroupConfig.setGroupName("数据库配置");
        templateMakerModelConfig.setModelGroupConfig(modelGroupConfig);

        // - 模型配置
        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig1 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig1.setFieldName("url");
        modelInfoConfig1.setType("String");
        modelInfoConfig1.setDefaultValue("jdbc:mysql://localhost:3306/my_db");
        modelInfoConfig1.setReplaceText("jdbc:mysql://localhost:3306/my_db");

        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig2 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig2.setFieldName("username");
        modelInfoConfig2.setType("String");
        modelInfoConfig2.setDefaultValue("root");
        modelInfoConfig2.setReplaceText("root");

        List<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList = Arrays.asList(modelInfoConfig1, modelInfoConfig2);
        templateMakerModelConfig.setModels(modelInfoConfigList);

        TemplateMakerConfig templateMakerConfig = new TemplateMakerConfig();
        templateMakerConfig.setId(1770339206246813696L);
        templateMakerConfig.setNewMeta(newMeta);
        templateMakerConfig.setTemplateMakerModelConfig(templateMakerModelConfig);
        templateMakerConfig.setTemplateMakerFileConfig(templateMakerFileConfig);
        templateMakerConfig.setOriginProjectPath(originProjectPath);
    }

}
