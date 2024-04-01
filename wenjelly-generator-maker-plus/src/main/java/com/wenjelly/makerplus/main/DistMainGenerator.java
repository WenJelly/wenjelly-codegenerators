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

    @Override
    protected String doDistDir(String outputRootPath, String jarPath) {
        return "制作简约生成器";
    }
}
