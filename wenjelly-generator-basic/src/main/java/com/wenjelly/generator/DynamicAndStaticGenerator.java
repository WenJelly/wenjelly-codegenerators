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

    public static void doGenerate(MainTemplateConfig model) throws TemplateException, IOException {

        // 模块目录根位置wenjelly-generators-basic
        String property = System.getProperty("user.dir");
        // 静态文件位置
        String staticInputPath = property + File.separator + "src/main/resources/templates/acm-template";
        // 静态文件输出位置
        String staticOutputPath = property + File.separator + "src/main/resources/templatesout";
        // 生成静态文件
        StaticGenerator.copyFileByHuTool(staticInputPath, staticOutputPath);

        // 模板文件位置
        String dynamicInputPath = property + File.separator + "src/main/resources/templates/Acmtemplate.java.ftl";
        // 目标输出位置
        String dynamicOutputPath = property + File.separator + "src/main/resources/templatesout"
                + File.separator + "acm-template/src/main/java/com/wenjelly/acm/Acmtemplate.java";
        // 生成动态文件
        DynamicGenerator.doGenerate(dynamicInputPath, dynamicOutputPath, model);
    }


}
