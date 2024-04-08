package com.wenjelly.maker.main;

/*
 * @time 2024/3/10 12:43
 * @package com.wenjelly.maker.main
 * @project wenjelly-generator-maker
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.wenjelly.maker.generator.JarGenerator;
import com.wenjelly.maker.generator.ScriptGenerator;
import com.wenjelly.maker.generator.file.DynamicFileGenerator;
import com.wenjelly.maker.meta.Meta;
import com.wenjelly.maker.meta.MetaManager;
import freemarker.template.TemplateException;


import java.io.File;
import java.io.IOException;

public abstract class MainGeneratorTemplate {

    public void doGenerator() throws TemplateException, IOException, InterruptedException {
        // 得到元信息
        Meta meta = MetaManager.getMetaObject();
        // 模板输出位置根路径 wenjelly-generator-maker/generator
        String outputRootPath = System.getProperty("user.dir") + File.separator + "generator"
                + File.separator + meta.getName();
        // 因为meta.getBasePackage得到的是com.wenjelly，这里将它变成com/wenjelly
        String basePackage = StrUtil.join("/", StrUtil.split(meta.getBasePackage(), "."));
        // wenjelly-generator-maker/generator/com/wenjelly
        String outputBasePackagePath = outputRootPath + File.separator + "src/main/java"
                + File.separator + basePackage;
        if (!FileUtil.exist(outputRootPath))
            FileUtil.mkdir(outputRootPath);
        // 模板输入位置根路径
        ClassPathResource resourcePath = new ClassPathResource("");
        String inputRootPath = resourcePath.getAbsolutePath() + File.separator + "templates";

        // 1、将最终代码的模板存放到.source目录中
        copyCodeTemplate(meta, outputRootPath);
        // 2、生成pom和README文件
        doPomAndREADME(inputRootPath, outputRootPath, meta);
        // 3、生成 cli 文件夹
        doCliDir(inputRootPath, outputBasePackagePath, meta);
        // 4、生成 generator 文件夹
        doGeneratorDir(inputRootPath, outputBasePackagePath, meta);
        // 5、生成 model 文件夹
        doModelDir(inputRootPath, outputBasePackagePath, meta);
        // 6、生成 Main 方法
        doMainFile(inputRootPath, outputBasePackagePath, meta);
        // 7、构建jar包并用程序封装成脚本
        String jarPath = doJarAndShell(outputRootPath, meta);
        // 8、构建精简版
        doDistDir(outputRootPath, jarPath);

    }

    protected void doDistDir(String outputRootPath, String jarPath) {
        // 这里用于构建精简版dist
        String shellPath = outputRootPath + File.separator + "generator";
        String outputSourcePath = outputRootPath + File.separator + ".source";
        String distOutputPath = outputRootPath + "-dist";
        String targetAbsolutePath = distOutputPath + File.separator + "target";
        FileUtil.mkdir(targetAbsolutePath);
        // 拷贝jar包
        String jarAbsolutePath = outputRootPath + File.separator + jarPath;
        FileUtil.copy(jarAbsolutePath, targetAbsolutePath, true);
        // 拷贝脚本程序
        FileUtil.copy(shellPath, distOutputPath, true);
        FileUtil.copy(shellPath + ".bat", distOutputPath, true);
        // 拷贝源码
        FileUtil.copy(outputSourcePath, distOutputPath, true);
    }

    protected String doJarAndShell(String outputRootPath, Meta meta) throws IOException, InterruptedException {
        // 构建jar包
        JarGenerator.doGenerate(outputRootPath);
        // 使用程序封装脚本
        String shellPath = outputRootPath + File.separator + "generator";
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        ScriptGenerator.doGenerate(shellPath, jarPath);
        return jarPath;
    }

    protected void copyCodeTemplate(Meta meta, String outputRootPath) {
        // 得到最终代码的原始路径
        String sourceRootPath = meta.getFileConfig().getSourceRootPath();
        String outputSourcePath = outputRootPath + File.separator + ".source";
        FileUtil.copy(sourceRootPath, outputSourcePath, true);
    }

    protected void doPomAndREADME(String inputRootPath, String outputRootPath, Meta meta) throws IOException, TemplateException {
        String outputFilePath;
        String inputFilePath;
        // 生成pom.xml文件
        inputFilePath = inputRootPath + File.separator + "pom.xml.ftl";
        outputFilePath = outputRootPath + File.separator + "pom.xml";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // 生成README.md文件
        inputFilePath = inputRootPath + File.separator + "README.md.ftl";
        outputFilePath = outputRootPath + File.separator + "README.md";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
    }

    protected void doMainFile(String inputRootPath, String outputBasePackagePath, Meta meta) throws IOException, TemplateException {
        String inputFilePath;
        String outputFilePath;
        // 生成 Main 文件
        inputFilePath = inputRootPath + File.separator + "java/Main.java.ftl";
        outputFilePath = outputBasePackagePath + File.separator + "Main.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
    }

    protected void doModelDir(String inputRootPath, String outputBasePackagePath, Meta meta) throws IOException, TemplateException {
        String inputFilePath;
        String outputFilePath;
        // 生成 DataModel 文件
        inputFilePath = inputRootPath + File.separator + "java/model/DataModel.java.ftl";
        outputFilePath = outputBasePackagePath + File.separator + "model/DataModel.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
    }

    protected void doGeneratorDir(String inputRootPath, String outputBasePackagePath, Meta meta) throws IOException, TemplateException {
        String outputFilePath;
        String inputFilePath;
        // 生成 StaticFileGenerator 文件
        inputFilePath = inputRootPath + File.separator + "java/generator/StaticFileGenerator.java.ftl";
        outputFilePath = outputBasePackagePath + File.separator + "generator/StaticFileGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
        // 生成 DynamicFileGenerator 文件
        inputFilePath = inputRootPath + File.separator + "java/generator/DynamicFileGenerator.java.ftl";
        outputFilePath = outputBasePackagePath + File.separator + "generator/DynamicFileGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
        // 生成 MainFileGenerator 文件
        inputFilePath = inputRootPath + File.separator + "java/generator/MainFileGenerator.java.ftl";
        outputFilePath = outputBasePackagePath + File.separator + "generator/MainFileGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
    }

    protected void doCliDir(String inputRootPath, String outputBasePackagePath, Meta meta) throws IOException, TemplateException {
        String inputFilePath;
        String outputFilePath;
        // 生成 ConfigCommand 文件
        inputFilePath = inputRootPath + File.separator + "java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = outputBasePackagePath + File.separator + "cli/command/ConfigCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
        // 生成 GenerateCommand 文件
        inputFilePath = inputRootPath + File.separator + "java/cli/command/GenerateCommand.java.ftl";
        outputFilePath = outputBasePackagePath + File.separator + "cli/command/GenerateCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
        // 生成 ListCommand 文件
        inputFilePath = inputRootPath + File.separator + "java/cli/command/ListCommand.java.ftl";
        outputFilePath = outputBasePackagePath + File.separator + "cli/command/ListCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
        // 生成 CommandExecutor 文件
        inputFilePath = inputRootPath + File.separator + "java/cli/CommandExecutor.java.ftl";
        outputFilePath = outputBasePackagePath + File.separator + "cli/CommandExecutor.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
        // 生成 JsonGenerateCommand文件
        inputFilePath = inputRootPath + File.separator + "java/cli/command/JsonGenerateCommand.java.ftl";
        outputFilePath = outputBasePackagePath + File.separator + "cli/command/JsonGenerateCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
    }


}
