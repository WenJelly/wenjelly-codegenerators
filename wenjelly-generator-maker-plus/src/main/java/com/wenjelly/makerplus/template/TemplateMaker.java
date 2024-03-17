package com.wenjelly.makerplus.template;

/*
 * @time 2024/3/16 14:40
 * @package com.wenjelly.makerplus.template
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.wenjelly.makerplus.meta.Meta;
import com.wenjelly.makerplus.meta.enums.FileGenerateTypeEnum;
import com.wenjelly.makerplus.meta.enums.FileTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TemplateMaker {

    public static void main(String[] args) {
        // 指定项目原始路径
        String property = System.getProperty("user.dir");
        String originProjectPath = new File(property).getParent() + File.separator + "wenjelly-generator-code-repositories/acm-template";

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
        String fileInputPath = "src/main/java/com/wenjelly/acm/MainTemplate.java";
        // 输出路径
        String fileOutputPath = fileInputPath + ".ftl";
        // 注意 win 系统需要对路径进行转义
        fileOutputPath = fileOutputPath.replace("\\", "/");

        // 3.输入模型参数信息（新信息）
        Meta.ModelConfigBean.ModelInfo modelInfo = new Meta.ModelConfigBean.ModelInfo();
        modelInfo.setFieldName("outputText");
        modelInfo.setType("String");
        modelInfo.setDescription("作者");
        modelInfo.setDefaultValue("Sum: ");

//        Meta.ModelConfigBean.ModelInfo modelInfo = new Meta.ModelConfigBean.ModelInfo();
//        modelInfo.setFieldName("author");
//        modelInfo.setType("String");
//        modelInfo.setDescription("作者");
//        modelInfo.setDefaultValue("WenJelly");

        // 4.输入文件参数信息（新信息）
        Meta.FileConfigBean.FileInfo fileInfo = new Meta.FileConfigBean.FileInfo();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setOutputPath(fileOutputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());

        makeTemplate(newMeta, 1769189523277221888L, modelInfo, fileInfo, originProjectPath, "Sum: ");
    }

    /**
     * 制作模板
     *
     * @param id
     * @param originProjectPath
     * @return
     */
    public static long makeTemplate(Meta newMeta, Long id, Meta.ModelConfigBean.ModelInfo modelInfo, Meta.FileConfigBean.FileInfo fileInfo, String originProjectPath, String searchStr) {

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

        // 2.输入文件信息
        // 得到最后一个雪花id的目录
        String sourceRootPath = templatePath + File.separator + FileUtil.getLastPathEle(Paths.get(originProjectPath)).toString();
        // 注意 win 系统需要对路径进行转义
        sourceRootPath = sourceRootPath.replace("\\", "/");
        if (!FileUtil.exist(fileInfo.getOutputPath())) {
            FileUtil.mkdir(fileInfo.getOutputPath());
        }

        // 二、字符串替换，将sum：替换为 ${outputText}，并生成ftl文件
        String fileInputAbsolutePath = sourceRootPath + File.separator + fileInfo.getInputPath();
        String fileOutputAbsolutePath = sourceRootPath + File.separator + fileInfo.getOutputPath();

        String fileContent = null;
        // 判断模板是否重复，如果有重复，说明需要再次挖坑
        if (FileUtil.exist(fileOutputAbsolutePath)) {
            // 说明已经生成过一次模板，直接读取最新的模板
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        } else {
            // 否则这是第一次制作，使用hutool工具类读取源文件信息
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }
        // 用于替换的内容
        String replacement = String.format("${%s}", modelInfo.getFieldName());
        // 对其进行替换
        String newFileContent = StrUtil.replace(fileContent, searchStr, replacement);
        // 输出模板文件
        FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);


        // 三、生成配置文件
        String metaJsonOutputPath = sourceRootPath + File.separator + "meta.json";
        System.out.println(metaJsonOutputPath);

        if (FileUtil.exist(metaJsonOutputPath)) {
            // meta.json文件存在，说明不是首次生成
            // 得到旧的meta.json信息
            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaJsonOutputPath), Meta.class);
            // 通过 BeanUtil.copyProperties 复制新对象的属性到老对象（如果属性为空则不复制），从而实现新老 meta 对象的合并。
            BeanUtil.copyProperties(newMeta, oldMeta, CopyOptions.create().ignoreNullValue());
            newMeta = oldMeta;

            // 得到原有的文件信息
            List<Meta.FileConfigBean.FileInfo> fileInfos = oldMeta.getFileConfig().getFiles();
            fileInfos.add(fileInfo);
            List<Meta.ModelConfigBean.ModelInfo> models = oldMeta.getModelConfig().getModels();
            models.add(modelInfo);

            // 对文件和模型去重
            oldMeta.getFileConfig().setFiles(distinctFiles(fileInfos));
            oldMeta.getModelConfig().setModels(distinctModels(models));

        } else {
            // 1.构造参数配置
            Meta.FileConfigBean fileConfigBean = new Meta.FileConfigBean();
            fileConfigBean.setSourceRootPath(sourceRootPath);
            ArrayList<Meta.FileConfigBean.FileInfo> fileInfos = new ArrayList<>();
            fileInfos.add(fileInfo);
            fileConfigBean.setFiles(fileInfos);
            newMeta.setFileConfig(fileConfigBean);

            Meta.ModelConfigBean modelConfigBean = new Meta.ModelConfigBean();
            ArrayList<Meta.ModelConfigBean.ModelInfo> modelInfos = new ArrayList<>();
            modelInfos.add(modelInfo);
            modelConfigBean.setModels(modelInfos);
            newMeta.setModelConfig(modelConfigBean);

        }
        // 更新元信息配置
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaJsonOutputPath);
        return id;
    }

    /**
     * 文件去重
     *
     * @param fileInfoList 文件内容列表
     * @return
     */
    public static List<Meta.FileConfigBean.FileInfo> distinctFiles(List<Meta.FileConfigBean.FileInfo> fileInfoList) {
        // 根据inputPath来进行去重
        ArrayList<Meta.FileConfigBean.FileInfo> newFileInfoList = new ArrayList<>(
                fileInfoList.stream()
                        .collect(
                                Collectors.toMap(Meta.FileConfigBean.FileInfo::getInputPath, o -> o, (e, r) -> r)

                        ).values()
        );
        return newFileInfoList;
    }


    /**
     * 模型去重
     *
     * @param modelInfoList 模型内容列表
     * @return
     */
    public static List<Meta.ModelConfigBean.ModelInfo> distinctModels(List<Meta.ModelConfigBean.ModelInfo> modelInfoList) {
        // 根据fieldName来进行去重
        ArrayList<Meta.ModelConfigBean.ModelInfo> newModelInfoList = new ArrayList<>(
                modelInfoList.stream()
                        .collect(
                                Collectors.toMap(Meta.ModelConfigBean.ModelInfo::getFieldName, o -> o, (e, r) -> r)
                        ).values()
        );
        return newModelInfoList;
    }

}
