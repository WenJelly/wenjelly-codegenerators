package com.wenjelly.generator;

/*
 * @time 2024/3/3 23:09
 * @package com.wenjelly.generator
 * @project wenjelly-generators
 * @author WenJelly
 */

import com.wenjelly.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 生成动态和静态，相当于结合
 */
public class DynamicAndStaticGenerator {

    private static String property = System.getProperty("user.dir"); //wenjelly-generators

    /*
     *静态目标输入位置
     */
    private static String staticInputPath;
    /**
     * 静态目标输出位置
     */
    private static String staticOutputPath;

    /**
     * 动态模板输入位置
     */
    private static String dynamicInputPath;
    /**
     * 动态模板输出位置
     */
    private static String dynamicOutputPath;

    /**
     * 完整生成（静态+动态）
     *
     * @param args
     */
    public static void main(String[] args) throws TemplateException, IOException {

    }

    public void doGenerate(MainTemplateConfig model) throws TemplateException, IOException {
        // 获取静态的输入位置
        staticInputPath = property + File.separator + "wenjelly-generator-demo-projects"
                + File.separator + "acm-template";
        // 获取静态的输出位置
        staticOutputPath = property + File.separator + "wenjelly-generator-basic"
                + File.separator + "src/main/resources/templatesout";
        // 生成静态文件
        StaticGenerator.copyFileByHuTool(staticInputPath, staticOutputPath);


        // 获取动态输入位置
        dynamicInputPath = property + File.separator + "wenjelly-generator-basic"
                + File.separator + "src/main/resources/templates/acmtemplate.java.ftl";
        // 获取动态输出位置
        dynamicOutputPath = property + File.separator + "wenjelly-generator-basic"
                + File.separator + "src/main/resources/templatesout"
                + File.separator + "acm-template/src/main/java/com/wenjelly/acm/MainTemplate.java";
        // 生成动态文件
        DynamicGenerator.doGenerate(dynamicInputPath, dynamicOutputPath, model);

    }


}
