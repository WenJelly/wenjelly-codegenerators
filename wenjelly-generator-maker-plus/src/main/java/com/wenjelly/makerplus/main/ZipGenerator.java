package com.wenjelly.makerplus.main;

/*
 * @time 2024/3/31 9:34
 * @package com.wenjelly.makerplus.main
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

/**
 * 继承主生成类，修改 buildDist 类 实现压缩
 */
public class ZipGenerator extends MainGeneratorTemplate {

    // 重写模板的构建精简方法，对生成的dist文件执行压缩
    @Override
    protected String buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        String distPath = super.buildDist(outputPath, sourceCopyDestPath, jarPath, shellOutputFilePath);
        return super.buildZip(distPath);
    }

}
