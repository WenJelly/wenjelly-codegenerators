package com.wenjelly.maker.generator.file;

/*
 * @time 2024/3/3 23:09
 * @package com.wenjelly.generator
 * @project wenjelly-generators
 * @author WenJelly
 */

import com.wenjelly.maker.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 生成动态和静态，相当于结合
 */
public class FileGenerator {

    /**
     * 完整生成（静态+动态）
     * @param args
     */
    public static void main(String[] args) throws TemplateException, IOException {

    }

    public void doGenerate(DataModel model) throws TemplateException, IOException {

        // 输入位置的根目录
        String inputRootPath = ".source/acm-template";
        // 输出位置的根目录
        String outputRootPath = "generated";
        // 最终输入路径 ： 输入位置的根目录 + 相对路径
        String inputPath;
        // 最终输出路径 ： 输出位置的根目录 + 相对路径
        String outputPath;

        boolean needGit = model.needGit;
        boolean loop = model.loop;


        // groupKey = git
        if (needGit) {
            inputPath = new File(inputRootPath,".gitignore").getAbsolutePath();
            outputPath = new File(outputRootPath,".gitignore").getAbsolutePath();
            // 生成静态文件
            StaticFileGenerator.copyFileByHuTool(inputPath,outputPath);
        }
        if (needGit) {
            inputPath = new File(inputRootPath,"README.md").getAbsolutePath();
            outputPath = new File(outputRootPath,"README.md").getAbsolutePath();
            // 生成静态文件
            StaticFileGenerator.copyFileByHuTool(inputPath,outputPath);
        }
        inputPath = new File(inputRootPath,"src/main/java/com/wenjelly/acm/acmtemplate.java.ftl").getAbsolutePath();
        outputPath = new File(outputRootPath,"acm-template-generator/src/com/wenjelly/acm/Acmtemplate.java").getAbsolutePath();
        // 生成动态文件
        DynamicFileGenerator.doGenerate(inputPath, outputPath, model);

    }


}
