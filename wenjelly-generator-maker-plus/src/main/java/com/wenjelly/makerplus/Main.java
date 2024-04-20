package com.wenjelly.makerplus;

/*
 * @time 2024/3/31 9:38
 * @package com.wenjelly.makerplus.generator
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import com.wenjelly.makerplus.main.MainGeneratorTemplate;
import com.wenjelly.makerplus.main.ZipGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        // 制作zip生成器，需要什么生成器就new对应的生成器类
        // MainGeneratorTemplate generator = new DistMainGenerator();
        MainGeneratorTemplate generator = new ZipGenerator();
        // 执行
        generator.doGenerate();
    }
}
