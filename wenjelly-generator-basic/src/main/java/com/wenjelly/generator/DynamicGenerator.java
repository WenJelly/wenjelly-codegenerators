package com.wenjelly.generator;

/*
 * @time 2024/3/3 22:18
 * @package com.wenjelly.generator
 * @project wenjelly-generators
 * @author WenJelly
 */


import com.wenjelly.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * 该类用于生成动态代码
 */
public class DynamicGenerator {
    /**
     * 生成动态文件
     *
     * @param inputPath 模板文件输入路径
     * @param outputPath 输出路径
     * @param model 数据模型
     * @throws IOException IOE异常
     * @throws TemplateException 模板异常
     */
    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为FreeMarker版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        // 指定模板文件所在的路径
        File templateDir  = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);
        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");
        configuration.setNumberFormat("0.######");
        // 获取模板文件并加载
        String fileName = new File(inputPath).getName();
        Template template = configuration.getTemplate(fileName);
        // 指定生成的文件,注意：目录一定要存在，否则会报错
        FileWriter fileWriter = new FileWriter(outputPath);
        // 将数据模型传递给模板并生成目标文件
        template.process(model,fileWriter);
        // 关闭输出流
        fileWriter.close();
    }
}
