package com.wenjelly.maker.main;

/*
 * @time 2024/3/11 20:43
 * @package com.wenjelly.maker.main
 * @project wenjelly-generator-maker
 * @author WenJelly
 */

import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;


import java.io.IOException;


public class DistMainGenerator extends MainGeneratorTemplate{

    @Override
    protected void doDistDir(String outputRootPath, String jarPath) {
        System.out.println("你好，这是精简方法");
    }

    @Test
    public void test() throws TemplateException, IOException, InterruptedException {
        super.doGenerator();
    }
}
