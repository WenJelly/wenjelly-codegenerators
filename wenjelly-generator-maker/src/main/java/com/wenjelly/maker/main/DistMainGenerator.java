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

/**
 * 通过模板方法设计模式，可以单独修改某一流程而不改变其他流程执行顺序
 */
public class DistMainGenerator extends MainGeneratorTemplate {

    @Test
    public void test() throws TemplateException, IOException, InterruptedException {
        super.doGenerator();
    }
}
