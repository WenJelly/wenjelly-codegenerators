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
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.wenjelly.makerplus.meta.Meta;
import com.wenjelly.makerplus.meta.enums.FileGenerateTypeEnum;
import com.wenjelly.makerplus.meta.enums.FileTypeEnum;
import com.wenjelly.makerplus.template.model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateMaker {

    /**
     * 测试
     */
    public static void main(String[] args) {

        // 第读取配置文件
        String springBootMeta = ResourceUtil.readUtf8Str("springbootmeta1.json");
        // 将配置文件转换成对象
        TemplateMakerConfig templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        // 制作模板与生成配置文件，下同
        makeTemplate(templateMakerConfig);

        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta2.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        makeTemplate(templateMakerConfig);

        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta3.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        makeTemplate(templateMakerConfig);


        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta4.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        makeTemplate(templateMakerConfig);

        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta5.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        makeTemplate(templateMakerConfig);

        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta6.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        makeTemplate(templateMakerConfig);

        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta7.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
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
        TemplateMakerOutputConfig templateMakerOutputConfig = templateMakerConfig.getTemplateMakerOutputConfig();
        // 调用制作模板方法
        return makeTemplate(newMeta, id, templateMakerModelConfig, templateMakerFileConfig, templateMakerOutputConfig, originProjectPath);
    }

    /**
     * 制作模板
     *
     * @param newMeta                  新的元信息文件，里面包含文件信息，模组信息
     * @param id                       工作空间的id，用于查看当前状态
     * @param templateMakerModelConfig 模型制作模型配置，里面包含了模型组信息，需要替换的字符串
     * @param templateMakerFileConfig  文件制作模板配置，里面包含了文件的路径，过滤条件，文件组等信息
     * @param originProjectPath        源文件的路径，用于将源文件复制到工作空间中
     * @return 返回工作空间的id
     */
    public static long makeTemplate(Meta newMeta, Long id, TemplateMakerModelConfig templateMakerModelConfig, TemplateMakerFileConfig templateMakerFileConfig, TemplateMakerOutputConfig templateMakerOutputConfig, String originProjectPath) {

        // 创建工作空间
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
            // 将源代码文件复制到工作空间中
            FileUtil.copy(originProjectPath, templatePath, true);
        }

        // 得到最后一个雪花id的目录
        // 获取第一个目录时，需要设置层级为 1
        String sourceRootPath = FileUtil.loopFiles(new File(templatePath), 1, null)
                .stream()
                .filter(File::isDirectory)
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getAbsolutePath();
        // 注意 win 系统需要对路径进行转义
        sourceRootPath = sourceRootPath.replace("\\", "/");
        // 获取新的模型配置列表
        ArrayList<Meta.ModelConfigBean.ModelInfo> newModelInfoList = getModelInfoList(templateMakerModelConfig);
        // 制作文件并获取新的文件配置列表
        ArrayList<Meta.FileConfigBean.FileInfo> newFileInfoList = getFileInfoList(templateMakerModelConfig, templateMakerFileConfig, sourceRootPath);
        // 生成配置文件
        makeMetaJson(newMeta, sourceRootPath, newFileInfoList, newModelInfoList, templateMakerOutputConfig);
        return id;
    }

    /**
     * 制作meta.json配置文件
     *
     * @param newMeta          新的元信息文件，里面包含文件信息，模组信息
     * @param sourceRootPath   需要生成代码的根路径
     * @param newFileInfoList  新文件配置信息列表，用于更新
     * @param newModelInfoList 新模型配置信息列表，用于更新
     * @return 返回新的元信息文件
     */
    private static Meta makeMetaJson(Meta newMeta, String sourceRootPath, ArrayList<Meta.FileConfigBean.FileInfo> newFileInfoList, ArrayList<Meta.ModelConfigBean.ModelInfo> newModelInfoList, TemplateMakerOutputConfig templateMakerOutputConfig) {
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
            if (newFiles == null) {
                newFiles = new ArrayList<>(newFileInfoList);
            } else {
                // 将新的文件信息添加至文件列表里
                newFiles.addAll(newFileInfoList);
            }

            // 得到原有的模型信息
            List<Meta.ModelConfigBean.ModelInfo> newModels = oldMeta.getModelConfig().getModels();
            if (newModels == null) {
                newModels = new ArrayList<>(newModelInfoList);
            } else {
                // 将新的模型信息添加至模型列表里
                newModels.addAll(newModelInfoList);
            }

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

        if (templateMakerOutputConfig != null) {
            if (templateMakerOutputConfig.isRemoveGroupFilesFromRoot()) {
                // 得到现在的meta文件配置信息
                List<Meta.FileConfigBean.FileInfo> fileInfoList = newMeta.getFileConfig().getFiles();
                // 内外去重,重新设置
                newMeta.getFileConfig().setFiles(TemplateMakerUtils.removeGroupFilesFromRoot(fileInfoList));
            }
        }

        // 更新元信息配置
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaJsonOutputPath);
        return newMeta;
    }

    /**
     * 处理文件操作
     *
     * @param templateMakerModelConfig 模型制作模型配置，里面包含了模型组信息，需要替换的字符串
     * @param templateMakerFileConfig  文件制作模板配置，里面包含了文件的路径，过滤条件，文件组等信息
     * @param sourceRootPath           需要生成代码的根路径
     * @return 返回一个用于更新的FileInfoList
     */
    private static ArrayList<Meta.FileConfigBean.FileInfo> getFileInfoList(TemplateMakerModelConfig templateMakerModelConfig, TemplateMakerFileConfig templateMakerFileConfig, String sourceRootPath) {

        // 本次新增的文件配置列表，用于生成meta.json文件
        ArrayList<Meta.FileConfigBean.FileInfo> newFileInfoList = new ArrayList<>();
        if (templateMakerFileConfig == null) {
            return newFileInfoList;
        }
        // 先从templateMakerFileConfig里面获得所有文件的信息
        List<TemplateMakerFileConfig.FileInfoConfig> files = templateMakerFileConfig.getFiles();
        if (files == null) {
            return newFileInfoList;
        }

        // 4.处理文件信息，制作模板，替换字符，生成.ftl模板
        ArrayList<Meta.FileConfigBean.FileInfo> fileInfoList = new ArrayList<>();
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

            for (File filteredFile : filteredFiles) {
                String fileInputAbsolutePath = filteredFile.getAbsolutePath();
                if (FileUtil.isDirectory(fileInputAbsolutePath)) {
                    // 如果file是文件夹，遍历得到文件列表
                    List<File> fileList = FileUtil.loopFiles(fileInputAbsolutePath);
                    for (File file : fileList) {
                        // 制作.ftl文件，即挖坑
                        Meta.FileConfigBean.FileInfo makedFileInfo = makeFileTemplate(file, sourceRootPath, templateMakerModelConfig, fileInfoConfig);
                        fileInfoList.add(makedFileInfo);
                    }
                } else {
                    // 如果file是单个文件，则直接进行制作即可
                    Meta.FileConfigBean.FileInfo makedFileInfo = makeFileTemplate(new File(fileInputAbsolutePath), sourceRootPath, templateMakerModelConfig, fileInfoConfig);
                    fileInfoList.add(makedFileInfo);
                }
            }


        }
        // 得到文件组信息
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = templateMakerFileConfig.getFileGroupConfig();
        // 先判断是否是一个文件组
        if (fileGroupConfig != null) {
            // 1.创建文件组并赋值
            Meta.FileConfigBean.FileInfo groupFileInfo = new Meta.FileConfigBean.FileInfo();
            groupFileInfo.setType(FileTypeEnum.GROUP.getValue());
            BeanUtil.copyProperties(fileGroupConfig, groupFileInfo);
            // 2.将文件列表添加到文件组里
            groupFileInfo.setFiles(fileInfoList);
            // 3.将文件组天加到文件配置列表里
            newFileInfoList.add(groupFileInfo);
        } else {
            // 如果不是文件组，直接将所有的文件列表添加到文件配置列表里面
            newFileInfoList.addAll(fileInfoList);
        }
        return newFileInfoList;
    }

    /**
     * 处理模型操作
     *
     * @param templateMakerModelConfig 模组制作模型配置，里面包含了模型组信息，需要替换的字符串
     * @return 返回一个用于更新的ModelInfoList
     */
    private static ArrayList<Meta.ModelConfigBean.ModelInfo> getModelInfoList(TemplateMakerModelConfig templateMakerModelConfig) {

        // 本次新增的模型配置列表，用于生成meta.json文件
        ArrayList<Meta.ModelConfigBean.ModelInfo> newModelInfoList = new ArrayList<>();
        if (templateMakerModelConfig == null) {
            return newModelInfoList;
        }
        // 3.处理模型信息
        List<TemplateMakerModelConfig.ModelInfoConfig> models = templateMakerModelConfig.getModels();
        if (models == null) {
            return newModelInfoList;
        }

        // 转化为配置接受的模型列表
        List<Meta.ModelConfigBean.ModelInfo> inputModelInfoList = models.stream().map(modelInfoConfig -> {
            Meta.ModelConfigBean.ModelInfo modelInfo = new Meta.ModelConfigBean.ModelInfo();
            // 将第一个参数的属性拷贝给第二个参数的属性，拷贝条件是第二个参数有一个或多个属性与第一个参数相同。
            BeanUtil.copyProperties(modelInfoConfig, modelInfo);
            return modelInfo;
        }).collect(Collectors.toList());

        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        // 先判断是否是一个模型组
        if (modelGroupConfig != null) {

            // 1.创建一个模型组
            Meta.ModelConfigBean.ModelInfo groupModelInfo = new Meta.ModelConfigBean.ModelInfo();
            BeanUtil.copyProperties(modelGroupConfig, groupModelInfo);

            // 2.将模型列表添加到模型组里
            groupModelInfo.setModels(inputModelInfoList);
            // 3.将模型组天加到模型配置列表里
            newModelInfoList.add(groupModelInfo);
        } else {
            // 如果不是模型组，直接将所有的模型列表添加到模型配置列表里面
            newModelInfoList.addAll(inputModelInfoList);
        }
        return newModelInfoList;
    }

    /**
     * 制作ftl文件并返回文件信息
     *
     * @param inputFile                文件输入的路径，可以是一个目录，也可以是单个文件
     * @param sourceRootPath           资源文件的根路径
     * @param templateMakerModelConfig 模板制作模型配置，里面包含了模型组信息，需要替换的字符串
     * @return 返回FileInfo类型，用于创建meta.json文件
     */
    public static Meta.FileConfigBean.FileInfo makeFileTemplate(File inputFile, String sourceRootPath, TemplateMakerModelConfig templateMakerModelConfig, TemplateMakerFileConfig.FileInfoConfig fileInfoConfig) {

        String fileInputAbsolutePath;
        // 先判断文件是否是以.ftl结尾，如果是，则为再制作
        boolean again = "ftl".equals(FileNameUtil.getSuffix(inputFile));
        if (again) {
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
        fileInfo.setCondition(fileInfoConfig.getCondition());
        fileInfo.setType(FileTypeEnum.FILE.getValue());

        if (!again && newFileContent.equals(fileContent)) {
            fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
        } else {
            fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
        }

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
