package com.wenjelly.makerplus.main;

/*
 * @time 2024/3/11 20:43
 * @package com.wenjelly.makerplus.main
 * @project wenjelly-generator-makerplus
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
