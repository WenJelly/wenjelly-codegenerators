package com.wenjelly.makerplus.main;

/*
 * @time 2024/3/11 20:43
 * @package com.wenjelly.makerplus.main
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */

/**
 * 继承主生成类，重写方法可以不影响其他执行流程的情况下实现新逻辑
 */
public class DistMainGenerator extends MainGeneratorTemplate {
    @Override
    protected String buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        System.out.println("制作简约生成器");
        return "";
    }
}
