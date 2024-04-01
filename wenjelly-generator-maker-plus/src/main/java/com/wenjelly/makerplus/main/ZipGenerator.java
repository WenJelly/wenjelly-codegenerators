package com.wenjelly.makerplus.main;

/*
 * @time 2024/3/31 9:34
 * @package com.wenjelly.makerplus.main
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ZipGenerator extends MainGeneratorTemplate{

    // 重写模板，执行压缩
    @Override
    protected String doDistDir(String outputRootPath, String jarPath) {
        String outputPath =  super.doDistDir(outputRootPath, jarPath);
        // 压缩
        return doZip(outputPath);
    }

}
