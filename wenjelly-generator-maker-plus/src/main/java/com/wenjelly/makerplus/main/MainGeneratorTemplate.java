package com.wenjelly.makerplus.main;

/*
 * @time 2024/3/10 12:43
 * @package com.wenjelly.makerplus.main
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.template.TemplateException;
import com.wenjelly.makerplus.generator.JarGenerator;
import com.wenjelly.makerplus.generator.ScriptGenerator;
import com.wenjelly.makerplus.generator.file.DynamicFileGenerator;
import com.wenjelly.makerplus.meta.Meta;
import com.wenjelly.makerplus.meta.MetaManager;

import java.io.File;
import java.io.IOException;

/**
 * 制作生成器的模板方法
 * 这里已标注好了各种执行顺序
 * 如果需要修改某一个顺序的执行内容，可以继承该类重写该方法
 */

public abstract class MainGeneratorTemplate {

    public void doGenerate() throws TemplateException, IOException, InterruptedException, freemarker.template.TemplateException {
        Meta meta = MetaManager.getMetaObject();
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
        doGenerate(meta, outputPath);
    }

    /**
     * 重载方法，参数不同
     *
     * @param meta       元信息配置
     * @param outputPath 生成后的文件输出位置
     * @throws TemplateException                     模板异常
     * @throws IOException                           IOE异常
     * @throws InterruptedException                  脚本执行异常
     * @throws freemarker.template.TemplateException 模板异常
     */
    public void doGenerate(Meta meta, String outputPath) throws TemplateException, IOException, InterruptedException, freemarker.template.TemplateException {
        if (!FileUtil.exist(outputPath)) {
            FileUtil.mkdir(outputPath);
        }

        // 1、复制源代码
        String sourceCopyDestPath = copySource(meta, outputPath);

        // 2、通过模板来生成代码生成器
        generateCode(meta, outputPath);

        // 3、构建 jar 包
        String jarPath = buildJar(meta, outputPath);

        // 4、封装脚本
        String shellOutputFilePath = buildScript(outputPath, jarPath);

        // 5、生成精简版的程序（产物包）
        buildDist(outputPath, sourceCopyDestPath, jarPath, shellOutputFilePath);
    }


    /**
     * 将源代码复制到指定目录，方便后续通过相对路径读取
     *
     * @param meta       元信息配置，这里用于读取源代码位置
     * @param outputPath 复制到该路径
     * @return 返回复制后的地址
     */
    protected String copySource(Meta meta, String outputPath) {
        String sourceRootPath = meta.getFileConfig().getSourceRootPath();
        String sourceCopyDestPath = outputPath + File.separator + ".source";
        FileUtil.copy(sourceRootPath, sourceCopyDestPath, false);
        return sourceCopyDestPath;
    }

    /**
     * 通过模板来生成代码生成器
     *
     * @param meta       生成器原信息配置
     * @param outputPath 生成位置
     * @throws IOException       IOE异常
     * @throws TemplateException 模板异常
     */
    protected void generateCode(Meta meta, String outputPath) throws IOException, TemplateException, freemarker.template.TemplateException {
        // 读取 resources 目录
        String inputResourcePath = "";

        // Java 包基础路径
        String outputBasePackage = meta.getBasePackage();
        String outputBasePackagePath = StrUtil.join("/", StrUtil.split(outputBasePackage, "."));
        String outputBaseJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;

        String inputFilePath;
        String outputFilePath;

        // model.DataModel
        inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/model/DataModel.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // cli.command.ConfigCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ConfigCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // cli.command.GenerateCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/GenerateCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/GenerateCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // cli.command.JsonGenerateCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/JsonGenerateCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/JsonGenerateCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // cli.command.ListCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ListCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ListCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // cli.CommandExecutor
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/CommandExecutor.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/CommandExecutor.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // Main
        inputFilePath = inputResourcePath + File.separator + "templates/java/Main.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/Main.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // generator.DynamicFileGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/DynamicFileGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/DynamicFileGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // generator.MainFileGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/MainFileGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/MainFileGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // generator.StaticFileGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/StaticFileGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/StaticFileGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // pom.xml
        inputFilePath = inputResourcePath + File.separator + "templates/pom.xml.ftl";
        outputFilePath = outputPath + File.separator + "pom.xml";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
    }

    /**
     * 构建 jar 包
     *
     * @param outputPath jar包生成在该目录下
     * @return 返回 jar 包的相对路径
     * @throws IOException          IOE异常
     * @throws InterruptedException 脚本执行异常
     */
    protected String buildJar(Meta meta, String outputPath) throws IOException, InterruptedException {
        JarGenerator.doGenerate(outputPath);
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        return jarPath;
    }

    /**
     * 封装脚本
     *
     * @param outputPath 脚本文件输出位置
     * @param jarPath    jar包的路径
     * @return 返回脚本路径
     * @throws IOException IOE异常
     */
    protected String buildScript(String outputPath, String jarPath) throws IOException {
        String shellOutputFilePath = outputPath + File.separator + "generator";
        ScriptGenerator.doGenerate(shellOutputFilePath, jarPath);
        return shellOutputFilePath;
    }

    /**
     * 生成精简版程序
     *
     * @param outputPath          精简版程序文件输出位置
     * @param sourceCopyDestPath  被拷贝的文件位置
     * @param jarPath             jar包位置
     * @param shellOutputFilePath 脚本位置
     * @return 产物包路径
     */
    protected String buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        String distOutputPath = outputPath + "-dist";
        // 拷贝 jar 包
        String targetAbsolutePath = distOutputPath + File.separator + "target";
        FileUtil.mkdir(targetAbsolutePath);
        String jarAbsolutePath = outputPath + File.separator + jarPath;
        FileUtil.copy(jarAbsolutePath, targetAbsolutePath, true);
        // 拷贝脚本文件
        FileUtil.copy(shellOutputFilePath, distOutputPath, true);
        FileUtil.copy(shellOutputFilePath + ".bat", distOutputPath, true);
        // 拷贝源模板文件
        FileUtil.copy(sourceCopyDestPath, distOutputPath, true);
        return distOutputPath;
    }

    /**
     * 制作压缩包
     *
     * @param outputPath 压缩包文件输出位置
     * @return 压缩包路径
     */
    protected String buildZip(String outputPath) {
        String zipPath = outputPath + ".zip";
        ZipUtil.zip(outputPath, zipPath);
        return zipPath;
    }


}
