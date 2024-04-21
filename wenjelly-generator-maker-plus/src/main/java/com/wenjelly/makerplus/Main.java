package com.wenjelly.makerplus;

/*
 * @time 2024/3/31 9:38
 * @package com.wenjelly.makerplus.generator
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.wenjelly.makerplus.main.MainGeneratorTemplate;
import com.wenjelly.makerplus.main.ZipGenerator;
import com.wenjelly.makerplus.template.TemplateMaker;
import com.wenjelly.makerplus.template.model.TemplateMakerConfig;
import freemarker.template.TemplateException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {

        // 第读取配置文件
        String springBootMeta = ResourceUtil.readUtf8Str("springbootmeta1.json");
        // 将配置文件转换成对象
        TemplateMakerConfig templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        // 制作模板与生成配置文件，下同
        TemplateMaker.makeTemplate(templateMakerConfig);

        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta2.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta3.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);


        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta4.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta5.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta6.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        springBootMeta = ResourceUtil.readUtf8Str("springbootmeta7.json");
        templateMakerConfig = JSONUtil.toBean(springBootMeta, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        // 制作zip生成器，需要什么生成器就new对应的生成器类
        // MainGeneratorTemplate generator = new DistMainGenerator();
        MainGeneratorTemplate generator = new ZipGenerator();
        // 执行
        generator.doGenerate();
    }
}
