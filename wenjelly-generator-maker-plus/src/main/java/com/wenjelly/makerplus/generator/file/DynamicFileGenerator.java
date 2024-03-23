package com.wenjelly.makerplus.generator.file;

/*
 * @time 2024/3/3 22:18
 * @package com.wenjelly.generator
 * @project wenjelly-generators
 * @author WenJelly
 */


import cn.hutool.core.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * 该类用于生成动态代码
 */
public class DynamicFileGenerator {
    /**
     * 生成动态文件
     *
     * @param inputPath  模板文件输入路径
     * @param outputPath 输出路径
     * @param model      数据模型
     * @throws IOException       IOE异常
     * @throws TemplateException 模板异常
     */
    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为FreeMarker版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        // 指定模板文件所在的路径
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);
        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");
        configuration.setNumberFormat("0.######");
        // 获取模板文件并加载
        String fileName = new File(inputPath).getName();
        Template template = configuration.getTemplate(fileName);

        // 如果文件不存在，就创建，这样后面就不会报错了
        if (!FileUtil.exist(outputPath)) {
            FileUtil.touch(outputPath);
        }
        FileWriter fileWriter = new FileWriter(outputPath);
        // 将数据模型传递给模板并生成目标文件
        template.process(model, fileWriter);
        // 关闭输出流
        fileWriter.close();
    }
}
