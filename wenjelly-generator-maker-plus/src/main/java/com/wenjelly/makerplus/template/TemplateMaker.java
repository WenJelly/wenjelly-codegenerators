package com.wenjelly.makerplus.template;

/*
 * @time 2024/3/16 14:40
 * @package com.wenjelly.makerplus.template
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
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
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TemplateMaker {

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

        makeTemplate(templateMakerConfig);
    }

    /**
     * 制作模板
     * 重载方法，方便调用
     *
     * @param templateMakerConfig 封装了制作模板的一系列参数，用于重载，方便最外层直接通过该封装类进行调用
     * @return 返回工作空间的id
     */
    public static long makeTemplate(TemplateMakerConfig templateMakerConfig) {

        Long id = templateMakerConfig.getId();
        Meta newMeta = templateMakerConfig.getNewMeta();
        TemplateMakerModelConfig templateMakerModelConfig = templateMakerConfig.getTemplateMakerModelConfig();
        TemplateMakerFileConfig templateMakerFileConfig = templateMakerConfig.getTemplateMakerFileConfig();
        String originProjectPath = templateMakerConfig.getOriginProjectPath();

        // 调用制作模板方法
        return makeTemplate(newMeta, id, templateMakerModelConfig, templateMakerFileConfig, originProjectPath);
    }


    /**
     * 制作模板
     *
     * @param newMeta                  新的元信息文件，里面包含文件信息，模组信息
     * @param id                       工作空间的id，用于查看当前状态
     * @param templateMakerModelConfig 模板制作模型配置，里面包含了模型组信息，需要替换的字符串
     * @param templateMakerFileConfig  文件制作模板配置，里面包含了文件的路径，过滤条件，文件组等信息
     * @param originProjectPath        源文件的路径，用于将源文件复制到工作空间中
     * @return 返回工作空间的id
     */
    public static long makeTemplate(Meta newMeta, Long id, TemplateMakerModelConfig templateMakerModelConfig, TemplateMakerFileConfig templateMakerFileConfig, String originProjectPath) {

        // 1.创建空间
        // 没有id，则生成
        if (id == null) {
            id = IdUtil.getSnowflakeNextId();
        }
        // 得到工作空间目录
        String property = System.getProperty("user.dir");
        String tempDirPath = property + File.separator + ".temp";
        String templatePath = tempDirPath + File.separator + id;
        // 如果文件不存在，则为首次制作，创建工作空间
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(templatePath);
            FileUtil.copy(originProjectPath, templatePath, true);
        }

        // 2.得到文件路径集合
        // 得到最后一个雪花id的目录
        String sourceRootPath = templatePath + File.separator + FileUtil.getLastPathEle(Paths.get(originProjectPath)).toString();
        // 注意 win 系统需要对路径进行转义
        sourceRootPath = sourceRootPath.replace("\\", "/");

        // 先从templateMakerFileConfig里面获得所有文件的信息
        List<TemplateMakerFileConfig.FileInfoConfig> files = templateMakerFileConfig.getFiles();
        // 用于收集满足条件的文件路径
        ArrayList<String> fileInputPathList = new ArrayList<>();
        for (TemplateMakerFileConfig.FileInfoConfig fileInfoConfig : files) {
            // 文件路径
            String filePath = fileInfoConfig.getPath();
            // 文件的过滤条件集合
            List<FileFilterConfig> filterConfigList = fileInfoConfig.getFilterConfigList();
            // 如果是相对路径，需要修改成绝对路径
            if (!filePath.startsWith(sourceRootPath)) {
                filePath = sourceRootPath + File.separator + filePath;
            }
            // 对文件进行过滤
            List<File> filteredFiles = FileFilter.doFilter(filePath, filterConfigList);
            // 将满足过滤条件的文件路径收集起来
            for (File file : filteredFiles) {
                String fileInputAbsolutePath = file.getAbsolutePath();
                fileInputPathList.add(fileInputAbsolutePath);
            }
        }

        // 3.处理模型信息
        List<TemplateMakerModelConfig.ModelInfoConfig> models = templateMakerModelConfig.getModels();
        // 转化为配置接受的模型列表
        List<Meta.ModelConfigBean.ModelInfo> inputModelInfoList = models.stream().map(modelInfoConfig -> {
            Meta.ModelConfigBean.ModelInfo modelInfo = new Meta.ModelConfigBean.ModelInfo();
            // 将第一个参数的属性拷贝给第二个参数的属性，拷贝条件是第二个参数有一个或多个属性与第一个参数相同。
            BeanUtil.copyProperties(modelInfoConfig, modelInfo);
            return modelInfo;
        }).collect(Collectors.toList());

        // 本次新增的模型配置列表，用于生成meta.json文件
        ArrayList<Meta.ModelConfigBean.ModelInfo> newModelInfoList = new ArrayList<>();
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        // 先判断是否是一个模型组
        if (modelGroupConfig != null) {

            // 如果是模型组的话，就先创建一个模型组，然后将模型列表添加到模型组里，最后再将模型组天加到模型配置列表里
            String groupKey = modelGroupConfig.getGroupKey();
            String groupName = modelGroupConfig.getGroupName();
            String condition = modelGroupConfig.getCondition();
            // 1.创建一个模型组
            Meta.ModelConfigBean.ModelInfo groupModelInfo = new Meta.ModelConfigBean.ModelInfo();
            groupModelInfo.setGroupKey(groupKey);
            groupModelInfo.setGroupName(groupName);
            groupModelInfo.setCondition(condition);
            // 2.将模型列表添加到模型组里
            groupModelInfo.setModels(inputModelInfoList);
            // 3.将模型组天加到模型配置列表里
            newModelInfoList.add(groupModelInfo);
        } else {
            // 如果不是模型组，直接将所有的模型列表添加到模型配置列表里面
            newModelInfoList.addAll(inputModelInfoList);
        }

        // 4.处理文件信息，制作模板，替换字符，生成.ftl模板
        ArrayList<Meta.FileConfigBean.FileInfo> fileInfoList = new ArrayList<>();
        for (String fileInputAbsolutePath : fileInputPathList) {
            if (FileUtil.isDirectory(fileInputAbsolutePath)) {
                // 如果file是文件夹，遍历得到文件列表
                List<File> fileList = FileUtil.loopFiles(fileInputAbsolutePath);
                for (File file : fileList) {
                    // 制作.ftl文件，即挖坑
                    Meta.FileConfigBean.FileInfo makedFileInfo = makeFileTemplate(file, sourceRootPath, templateMakerModelConfig);
                    fileInfoList.add(makedFileInfo);
                }
            } else {
                // 如果file是单个文件，则直接进行制作即可
                Meta.FileConfigBean.FileInfo makedFileInfo = makeFileTemplate(new File(fileInputAbsolutePath), sourceRootPath, templateMakerModelConfig);
                fileInfoList.add(makedFileInfo);
            }
        }

        // 本次新增的文件配置列表，用于生成meta.json文件
        ArrayList<Meta.FileConfigBean.FileInfo> newFileInfoList = new ArrayList<>();
        // 得到文件组信息
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = templateMakerFileConfig.getFileGroupConfig();
        // 先判断是否是一个文件组
        if (fileGroupConfig != null) {

            // 如果是文件夹组的话，就先创建一个文件组，然后将文件列表添加到文件组里，最后再将文件组天加到文件配置列表里
            String condition = fileGroupConfig.getCondition();
            String groupKey = fileGroupConfig.getGroupKey();
            String groupName = fileGroupConfig.getGroupName();
            // 1.创建文件组并赋值
            Meta.FileConfigBean.FileInfo groupFileInfo = new Meta.FileConfigBean.FileInfo();
            groupFileInfo.setType(FileTypeEnum.GROUP.getValue());
            groupFileInfo.setGroupKey(groupKey);
            groupFileInfo.setGroupName(groupName);
            groupFileInfo.setCondition(condition);
            // 2.将文件列表添加到文件组里
            groupFileInfo.setFiles(fileInfoList);
            // 3.将文件组天加到文件配置列表里
            newFileInfoList.add(groupFileInfo);
        } else {
            // 如果不是文件组，直接将所有的文件列表添加到文件配置列表里面
            newFileInfoList.addAll(fileInfoList);
        }

        // 5.生成配置文件
        String metaJsonOutputPath = sourceRootPath + File.separator + "meta.json";

        if (FileUtil.exist(metaJsonOutputPath)) {
            // meta.json文件存在，说明不是首次生成
            // 先得到旧的meta.json信息
            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaJsonOutputPath), Meta.class);
            // 通过 BeanUtil.copyProperties 复制新对象的属性到老对象（如果属性为空则不复制），从而实现新老 meta 对象的合并。
            BeanUtil.copyProperties(newMeta, oldMeta, CopyOptions.create().ignoreNullValue());
            newMeta = oldMeta;

            // 得到原有的文件信息
            List<Meta.FileConfigBean.FileInfo> newFiles = oldMeta.getFileConfig().getFiles();
            // 将新的文件信息添加至文件列表里
            newFiles.addAll(newFileInfoList);

            // 得到原有的模型信息
            List<Meta.ModelConfigBean.ModelInfo> newModels = oldMeta.getModelConfig().getModels();
            // 将新的模型信息添加至模型列表里
            newModels.addAll(newModelInfoList);

            // 对文件和模型去重
            newMeta.getFileConfig().setFiles(distinctFiles(newFiles));
            newMeta.getModelConfig().setModels(distinctModels(newModels));
        } else {
            // 1.构造文件参数配置
            Meta.FileConfigBean fileConfigBean = new Meta.FileConfigBean();
            fileConfigBean.setSourceRootPath(sourceRootPath);
            fileConfigBean.setFiles(newFileInfoList);
            newMeta.setFileConfig(fileConfigBean);
            // 2.构造模型参数配置
            Meta.ModelConfigBean modelConfigBean = new Meta.ModelConfigBean();
            ArrayList<Meta.ModelConfigBean.ModelInfo> modelInfos = new ArrayList<>(newModelInfoList);
            modelConfigBean.setModels(modelInfos);
            newMeta.setModelConfig(modelConfigBean);
        }
        // 更新元信息配置
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaJsonOutputPath);
        return id;
    }

    /**
     * 制作ftl文件并返回文件信息
     *
     * @param inputFile                文件输入的路径，可以是一个目录，也可以是单个文件
     * @param sourceRootPath           资源文件的根路径
     * @param templateMakerModelConfig 模板制作模型配置，里面包含了模型组信息，需要替换的字符串
     * @return 返回FileInfo类型，用于创建meta.json文件
     */
    public static Meta.FileConfigBean.FileInfo makeFileTemplate(File inputFile, String sourceRootPath, TemplateMakerModelConfig templateMakerModelConfig) {

        String fileInputAbsolutePath;
        // 先判断文件是否是以.ftl结尾，如果是，则为再制作
        if ("ftl".equals(FileNameUtil.getSuffix(inputFile))) {
            fileInputAbsolutePath = inputFile.getAbsolutePath().replace("\\", "/").replace(".ftl", "");
        } else {
            fileInputAbsolutePath = inputFile.getAbsolutePath().replace("\\", "/");
        }

        // 得到文件的输入、输出绝对路径，并转换一下格式
        String fileOutputAbsolutePath = fileInputAbsolutePath + ".ftl";

        // 得到相对路径，用于配置meta.json
        String fileInputPath = fileInputAbsolutePath.replace(sourceRootPath + "/", "");
        String fileOutputPath = fileInputPath + ".ftl";

        String fileContent;
        // 判断模板是否重复，如果有重复，说明需要再次挖坑
        if (FileUtil.exist(fileOutputAbsolutePath)) {
            // 说明已经生成过一次模板，直接读取最新的模板
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        } else {
            // 否则这是第一次制作，使用hutool工具类读取源文件信息
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }

        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        String newFileContent = fileContent;
        // 需要替换的内容
        String replacement;
        for (TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig : templateMakerModelConfig.getModels()) {
            // 如果不是分组
            if (modelGroupConfig == null) {
                replacement = String.format("${%s}", modelInfoConfig.getFieldName());
            } else {
                // 如果是分组，需要在外围再加上一层分组
                String groupKey = modelGroupConfig.getGroupKey();
                replacement = String.format("${%s.%s}", groupKey, modelInfoConfig.getFieldName());
            }
            // 多次替换
            newFileContent = StrUtil.replace(newFileContent, modelInfoConfig.getReplaceText(), replacement);
        }
        // 输出模板文件
        FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);

        // 设置配置信息
        Meta.FileConfigBean.FileInfo fileInfo = new Meta.FileConfigBean.FileInfo();
        fileInfo.setInputPath(fileOutputPath);
        fileInfo.setOutputPath(fileInputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());

        return fileInfo;
    }

    /**
     * 文件去重
     *
     * @param fileInfoList 文件内容列表
     * @return 返回FileInfo类型，用于创建meta.json文件
     */
    public static List<Meta.FileConfigBean.FileInfo> distinctFiles(List<Meta.FileConfigBean.FileInfo> fileInfoList) {

        // 策略：同分组内文件 merge，不同分组保留
        Map<String, List<Meta.FileConfigBean.FileInfo>> groupKeyFileInfoListMap = fileInfoList
                .stream()
                .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
                .collect(
                        Collectors.groupingBy(Meta.FileConfigBean.FileInfo::getGroupKey)
                );

        // 2. 同组内的文件配置合并
        // 保存每个组对应的合并后的对象 map
        Map<String, Meta.FileConfigBean.FileInfo> groupKeyMergedFileInfoMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.FileConfigBean.FileInfo>> entry : groupKeyFileInfoListMap.entrySet()) {
            List<Meta.FileConfigBean.FileInfo> tempFileInfoList = entry.getValue();
            List<Meta.FileConfigBean.FileInfo> newFileInfoList = new ArrayList<>(tempFileInfoList.stream()
                    .flatMap(fileInfo -> fileInfo.getFiles().stream())
                    .collect(
                            Collectors.toMap(Meta.FileConfigBean.FileInfo::getOutputPath, o -> o, (e, r) -> r)
                    ).values());

            // 使用新的 group 配置
            Meta.FileConfigBean.FileInfo newFileInfo = CollUtil.getLast(tempFileInfoList);
            newFileInfo.setFiles(newFileInfoList);
            String groupKey = entry.getKey();
            groupKeyMergedFileInfoMap.put(groupKey, newFileInfo);
        }

        // 3. 将文件分组添加到结果列表
        List<Meta.FileConfigBean.FileInfo> resultList = new ArrayList<>(groupKeyMergedFileInfoMap.values());

        // 4. 将未分组的文件添加到结果列表
        List<Meta.FileConfigBean.FileInfo> noGroupFileInfoList = fileInfoList.stream().filter(fileInfo -> StrUtil.isBlank(fileInfo.getGroupKey()))
                .collect(Collectors.toList());
        resultList.addAll(new ArrayList<>(noGroupFileInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.FileConfigBean.FileInfo::getInputPath, o -> o, (e, r) -> r)
                ).values()));
        return resultList;
    }


    /**
     * 模型去重
     *
     * @param modelInfoList 模型内容列表
     * @return 返回ModelInfo类型，用于创建meta.json文件
     */
    public static List<Meta.ModelConfigBean.ModelInfo> distinctModels(List<Meta.ModelConfigBean.ModelInfo> modelInfoList) {
        // 策略：同分组内模型 merge，不同分组保留

        // 1. 有分组的，以组为单位划分
        Map<String, List<Meta.ModelConfigBean.ModelInfo>> groupKeyModelInfoListMap = modelInfoList
                .stream()
                .filter(modelInfo -> StrUtil.isNotBlank(modelInfo.getGroupKey()))
                .collect(
                        Collectors.groupingBy(Meta.ModelConfigBean.ModelInfo::getGroupKey)
                );


        // 2. 同组内的模型配置合并
        // 保存每个组对应的合并后的对象 map
        Map<String, Meta.ModelConfigBean.ModelInfo> groupKeyMergedModelInfoMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.ModelConfigBean.ModelInfo>> entry : groupKeyModelInfoListMap.entrySet()) {
            List<Meta.ModelConfigBean.ModelInfo> tempModelInfoList = entry.getValue();
            List<Meta.ModelConfigBean.ModelInfo> newModelInfoList = new ArrayList<>(tempModelInfoList.stream()
                    .flatMap(modelInfo -> modelInfo.getModels().stream())
                    .collect(
                            Collectors.toMap(Meta.ModelConfigBean.ModelInfo::getFieldName, o -> o, (e, r) -> r)
                    ).values());

            // 使用新的 group 配置
            Meta.ModelConfigBean.ModelInfo newModelInfo = CollUtil.getLast(tempModelInfoList);
            newModelInfo.setModels(newModelInfoList);
            String groupKey = entry.getKey();
            groupKeyMergedModelInfoMap.put(groupKey, newModelInfo);
        }

        // 3. 将模型分组添加到结果列表
        List<Meta.ModelConfigBean.ModelInfo> resultList = new ArrayList<>(groupKeyMergedModelInfoMap.values());

        // 4. 将未分组的模型添加到结果列表
        List<Meta.ModelConfigBean.ModelInfo> noGroupModelInfoList = modelInfoList.stream().filter(modelInfo -> StrUtil.isBlank(modelInfo.getGroupKey()))
                .collect(Collectors.toList());
        resultList.addAll(new ArrayList<>(noGroupModelInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.ModelConfigBean.ModelInfo::getFieldName, o -> o, (e, r) -> r)
                ).values()));
        return resultList;
    }

}
