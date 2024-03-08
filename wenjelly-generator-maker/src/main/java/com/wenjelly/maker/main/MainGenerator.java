package com.wenjelly.maker.main;

/*
 * @time 2024/3/8 11:02
 * @package com.wenjelly.maker.main
 * @project wenjelly-generator-maker
 * @author WenJelly
 */

import cn.hutool.Hutool;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.wenjelly.maker.generator.file.DynamicFileGenerator;
import com.wenjelly.maker.meta.Meta;
import com.wenjelly.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException {
        Meta meta = MetaManager.getMetaObject();

        String projectPath  = System.getProperty("user.dir");
        // 输出根路径
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
        if (!FileUtil.exist(outputPath)){
            FileUtil.mkdir(outputPath);
        }

        // Java 包基础路径
        String basePackage = meta.getBasePackage(); //com.wenjelly
        // 将基础包路径进行分割
        List<String> split = StrUtil.split(basePackage, "."); // ["com","wenjelly"]
        // 将分割后的数组以/连接起来
        String join = StrUtil.join("/", split); // com/wenjelly
        // 输出路径 = 输出根路径 + "src/main/java" + 包基础路径 + "/model/DataModel.java"
        String outputFilePath = outputPath + File.separator + "src/main/java/" + File.separator + join
                + "/model/DataModel.java";

        // 读取 resources 目录
        ClassPathResource classPathResource = new ClassPathResource("");
        // 模板文件路径
        String inputResourcePath = classPathResource.getAbsolutePath();
        String inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";


        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

    }

}
