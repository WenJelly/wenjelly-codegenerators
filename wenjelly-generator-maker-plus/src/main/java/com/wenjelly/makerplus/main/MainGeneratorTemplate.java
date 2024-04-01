package com.wenjelly.makerplus.main;

/*
 * @time 2024/3/10 12:43
 * @package com.wenjelly.makerplus.main
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
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
import java.util.List;

/**
 * 制作生成器的模板方法
 * 这里已标注好了各种执行顺序
 * 如果需要修改某一个顺序的执行内容，可以继承该类重写该方法
 */

public abstract class MainGeneratorTemplate {

    public void doGenerator() throws TemplateException, IOException, InterruptedException, freemarker.template.TemplateException {

        // 得到元信息
        Meta meta = MetaManager.getMetaObject();
        // 模板输出位置根路径
        String outputRootPath = System.getProperty("user.dir") + File.separator + "generator"
                + File.separator + meta.getName();
        if (!FileUtil.exist(outputRootPath))
            FileUtil.mkdir(outputRootPath);
        // 因为meta.getBasePackage得到的是com.xxx，这里将它变成com/xxx
        String basePackage = StrUtil.join("/", StrUtil.split(meta.getBasePackage(), "."));
        String outputBasePackagePath = outputRootPath + File.separator + "src/main/java"
                + File.separator + basePackage;

        // 得到模板资源路径
        ClassPathResource classPathResource = new ClassPathResource("");
        String resourceAbsolutePath = classPathResource.getAbsolutePath();
        String inputRootPath = resourceAbsolutePath + File.separator + "templates";

        // 1、将源代码复制到指定目录，方便后续通过相对路径读取
        copyCodeTemplate(meta, outputRootPath);
        // 2、生成目标代码的Pom和README文件
        doPomAndREADME(inputRootPath, outputRootPath, meta);
        // 3、生成Cli文件夹，执行终端命令
        doCliDir(inputRootPath, outputBasePackagePath, meta);
        // 4、生成Generator文件夹 制作动态+静态的目标代码
        doGeneratorDir(inputRootPath, outputBasePackagePath, meta);
        // 5、生成Model文件夹，给源代码模板提供数据模型
        doModelDir(inputRootPath, outputBasePackagePath, meta);
        // 6、生成Main文件，接收终端的输入信息
        doMainFile(inputRootPath, outputBasePackagePath, meta);
        // 7、构建jar包并用程序封装成脚本
        String jarPath = doJarAndShell(outputRootPath, meta);
        // 8、构建精简版
        doDistDir(outputRootPath, jarPath);

    }

    /**
     * 根据makerFileName生成指定文件
     *
     * @param inputRootPath         生成器模板文件的根目录
     * @param outputBasePackagePath 输出路径
     * @param meta                  元信息配置，包括需要替换的模型，通过模型来生成指定的生成器目标代码
     * @throws IOException                           IOE异常
     * @throws freemarker.template.TemplateException 模板引擎的模板异常
     */
    protected void doMakerFile(String inputRootPath, String outputBasePackagePath, Meta meta, String makerFileName) throws freemarker.template.TemplateException, IOException {
        // 生成器目标代码的输出位置的根路径
        String outputFilePath;
        // 生成器模板代码的位置
        String inputFilePath;
        if (FileUtil.isDirectory(inputRootPath)) {
            // 如果是文件夹
            List<File> fileList = FileUtil.loopFiles(inputRootPath);
            for (File file : fileList) {
                // 判断是否是需要生成的文件
                if (StrUtil.contains(file.getName(), makerFileName)) {
                    // 得到生成器模板代码的位置
                    inputFilePath = file.getAbsolutePath();
                    // 以java文件夹分割
                    List<String> split = StrUtil.split(inputFilePath, "\\java");
                    String fileName = split.get(split.size() - 1).replace("\\", "/");
                    // 得到java文件后面的文件路径
                    String outputPath = StrUtil.sub(fileName, 0, fileName.length() - 4);
                    // 输出位置的根路径 + 后面文件的相对路径
                    outputFilePath = outputBasePackagePath + outputPath;
                    // 调用动态生成的代码
                    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
                }
            }
        }
    }

    /**
     * 将源代码复制到指定目录，方便后续通过相对路径读取
     *
     * @param meta           元信息配置，通过元信息内容来生成目标代码内容，此处用于得到源代码的路径，用于复制
     * @param outputRootPath 输出路径，将源代码复制到该路径下
     */
    protected void copyCodeTemplate(Meta meta, String outputRootPath) {
        // 得到源代码的原始路径
        String sourceRootPath = meta.getFileConfig().getSourceRootPath();
        // .source用于存放源代码
        String outputSourcePath = outputRootPath + File.separator + ".source";
        // 将源代码复制到该目录下
        FileUtil.copy(sourceRootPath, outputSourcePath, true);
    }

    /**
     * 生成目标代码的Pom和README文件
     *
     * @param inputRootPath  模板文件的根目录
     * @param outputRootPath 目标代码的输出位置
     * @param meta           元信息配置，包括需要替换的模型
     * @throws IOException                           IOE异常
     * @throws freemarker.template.TemplateException 模板引擎的模板异常
     */
    protected void doPomAndREADME(String inputRootPath, String outputRootPath, Meta meta) throws IOException, freemarker.template.TemplateException {
        // 输出路径
        String outputFilePath;
        // 模板输入路径
        String inputFilePath;

        inputFilePath = inputRootPath + File.separator + "pom.xml.ftl";
        outputFilePath = outputRootPath + File.separator + "pom.xml";
        // 生成pom.xml文件
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        inputFilePath = inputRootPath + File.separator + "README.md.ftl";
        outputFilePath = outputRootPath + File.separator + "README.md";
        // 生成README.md文件
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
    }

    /**
     * 生成Cli文件夹，执行终端命令
     *
     * @param inputRootPath         生成器模板文件的根目录
     * @param outputBasePackagePath 输出路径
     * @param meta                  元信息配置，包括需要替换的模型，通过模型来生成指定的生成器目标代码
     * @throws IOException                           IOE异常
     * @throws freemarker.template.TemplateException 模板引擎的模板异常
     */
    protected void doCliDir(String inputRootPath, String outputBasePackagePath, Meta meta) throws IOException, freemarker.template.TemplateException {
        String makerFileName = "Command";
        doMakerFile(inputRootPath, outputBasePackagePath, meta, makerFileName);
    }

    /**
     * 生成Generator文件夹 制作动态+静态的目标代码
     *
     * @param inputRootPath         生成器模板文件的根目录
     * @param outputBasePackagePath 输出路径
     * @param meta                  元信息配置，包括需要替换的模型，通过模型来生成指定的生成器目标代码
     * @throws IOException                           IOE异常
     * @throws freemarker.template.TemplateException 模板引擎的模板异常
     */
    protected void doGeneratorDir(String inputRootPath, String outputBasePackagePath, Meta meta) throws IOException, freemarker.template.TemplateException {
        String makerFileName = "Generator";
        doMakerFile(inputRootPath, outputBasePackagePath, meta, makerFileName);
    }

    /**
     * 生成Model文件夹，给源代码模板提供数据模型
     *
     * @param inputRootPath         生成器模板文件的根目录
     * @param outputBasePackagePath 输出路径
     * @param meta                  元信息配置，包括需要替换的模型，通过模型来生成指定的生成器目标代码
     * @throws IOException                           IOE异常
     * @throws freemarker.template.TemplateException 模板引擎的模板异常
     */
    protected void doModelDir(String inputRootPath, String outputBasePackagePath, Meta meta) throws IOException, freemarker.template.TemplateException {
        String makerFileName = "Model";
        doMakerFile(inputRootPath, outputBasePackagePath, meta, makerFileName);
    }

    /**
     * 生成Main文件，接收终端的输入信息
     *
     * @param inputRootPath         生成器模板文件的根目录
     * @param outputBasePackagePath 输出路径
     * @param meta                  元信息配置，包括需要替换的模型，通过模型来生成指定的生成器目标代码
     * @throws IOException                           IOE异常
     * @throws freemarker.template.TemplateException 模板引擎的模板异常
     */
    protected void doMainFile(String inputRootPath, String outputBasePackagePath, Meta meta) throws IOException, TemplateException, freemarker.template.TemplateException {
        String inputFilePath;
        String outputFilePath;
        // 生成 Main 文件
        inputFilePath = inputRootPath + File.separator + "java/Main.java.ftl";
        outputFilePath = outputBasePackagePath + File.separator + "Main.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
    }

    /**
     * 构建jar包并用程序封装成脚本
     *
     * @param outputRootPath jar包的输出路径
     * @param meta           元信息配置，包含目标代码的一系列信息
     * @return 返回jar包路径
     * @throws IOException          IOE异常
     * @throws InterruptedException 中断异常
     */
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

    protected String doDistDir(String outputRootPath, String jarPath) {
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
        return distOutputPath;
    }

    /**
     * 压缩文件包
     *
     * @param outputPath 需要压缩的文件位置
     * @return 压缩后的位置
     */
    protected String doZip(String outputPath) {
        String zipPath = outputPath + ".zip";
        ZipUtil.zip(outputPath, zipPath);
        return zipPath;
    }


}
