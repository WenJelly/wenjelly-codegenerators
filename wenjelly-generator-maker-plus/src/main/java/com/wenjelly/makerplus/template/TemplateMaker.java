package com.wenjelly.makerplus.template;

/*
 * @time 2024/3/16 14:40
 * @package com.wenjelly.makerplus.template
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.wenjelly.makerplus.meta.Meta;
import com.wenjelly.makerplus.meta.enums.FileGenerateTypeEnum;
import com.wenjelly.makerplus.meta.enums.FileTypeEnum;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TemplateMaker {

    public static void main(String[] args) {

        // 一、输入信息
        // 1.基本文件信息
        String name = "acm-template";
        String description = "ACM示例模板生成器";
        String version = "1.0";
        String author = "wenjelly";

        // 2.输入文件信息
        String property = System.getProperty("user.dir");
        String sourceRootPath = new File(property).getParent() + File.separator + "wenjelly-generator-code-repositories/acm-template";
        // 注意 win 系统需要对路径进行转义
        sourceRootPath = sourceRootPath.replace("\\", "/");
        // 输入路径
        String fileInputPath = "src/main/java/com/wenjelly/acm/MainTemplate.java";
        // 输出路径
        // todo 输出路径需要修改
        String fileOutputPath = property + "/MainTemplate.java.ftl";
        // 注意 win 系统需要对路径进行转义
        fileOutputPath = fileInputPath.replace("\\\\","/");

        // 3.输入模型参数信息
        Meta.ModelConfigBean.ModelInfo modelInfo = new Meta.ModelConfigBean.ModelInfo();
        modelInfo.setFieldName("outputText");
        modelInfo.setType("String");
        modelInfo.setDescription("输出信息");
        modelInfo.setDefaultValue("Sum ： ");

        // 二、字符串替换，将sum：替换为 ${outputText}，并生成ftl文件
        String fileInputAbsolutePath = sourceRootPath + File.separator + fileInputPath;
        // 使用hutool工具类读取源文件信息
        String fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        // 用于替换的内容
        String replacement = String.format("${%s}", modelInfo.getFieldName());
        // 对其进行替换
        String newFileContent = StrUtil.replace(fileContent, "Sum: ", replacement);

        // 输出模板文件
        // todo 输出的绝对路径也需要修改
        String fileOutputAbsolutePath = fileOutputPath;
        System.out.println(fileOutputAbsolutePath);
        FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);

        // 三、生成配置文件
        // todo meta输出路径也要修改
        String metaJsonOutputPath = property + File.separator + "/meta.json";

        // 1.构造参数配置
        Meta meta = new Meta();
        meta.setName(name);
        meta.setDescription(description);
        meta.setVersion(version);
        meta.setAuthor(author);

        Meta.FileConfigBean fileConfigBean = new Meta.FileConfigBean();
        fileConfigBean.setSourceRootPath(sourceRootPath);
        ArrayList<Meta.FileConfigBean.FileInfo> fileInfos = new ArrayList<>();
        Meta.FileConfigBean.FileInfo fileInfo = new Meta.FileConfigBean.FileInfo();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setOutputPath(fileOutputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
        fileInfos.add(fileInfo);
        fileConfigBean.setFiles(fileInfos);
        meta.setFileConfig(fileConfigBean);

        Meta.ModelConfigBean modelConfigBean = new Meta.ModelConfigBean();
        ArrayList<Meta.ModelConfigBean.ModelInfo> modelInfos = new ArrayList<>();
        modelInfos.add(modelInfo);
        modelConfigBean.setModels(modelInfos);
        meta.setModelConfig(modelConfigBean);

        // 将对象转换成json，toJsonPrettyStr能够格式化，比较方便
        String jsonPrettyStr = JSONUtil.toJsonPrettyStr(meta);
        // 写入meta.json中
        FileUtil.writeUtf8String(jsonPrettyStr,metaJsonOutputPath);


    }

}
