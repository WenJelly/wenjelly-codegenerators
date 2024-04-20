package com.wenjelly.makerplus.main;

/*
 * @time 2024/3/31 9:34
 * @package com.wenjelly.makerplus.main
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

public class ZipGenerator extends MainGeneratorTemplate {

    // 重写模板，执行压缩
    @Override
    protected String buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        String distPath = super.buildDist(outputPath, sourceCopyDestPath, jarPath, shellOutputFilePath);
        return super.buildZip(distPath);
    }

}
