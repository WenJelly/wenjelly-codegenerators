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

    @Test
    public void test() throws TemplateException, IOException, InterruptedException {
        super.doGenerator();
    }
}
