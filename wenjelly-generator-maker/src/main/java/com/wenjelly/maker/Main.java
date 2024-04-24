package com.wenjelly.maker;

/*
 * @time 2024/3/6 19:52
 * @package com.wenjelly
 * @project wenjelly-generators
 * @author WenJelly
 */


import com.wenjelly.maker.cli.CommandExecutor;
import com.wenjelly.maker.main.DistMainGenerator;
import com.wenjelly.maker.main.MainGeneratorTemplate;
import freemarker.template.TemplateException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        MainGeneratorTemplate distMainGenerator = new DistMainGenerator();
        distMainGenerator.doGenerator();
    }
}
