package com.wenjelly.maker.generator.file;

/*
 * @time 2024/3/3 10:06
 * @package com.wenjelly.generator
 * @project wenjelly-generators
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;

/**
 * 该类用于生成静态代码
 */


public class StaticFileGenerator {

    /**
     * 用于拷贝文件，使用HuTool工具类，会将输入目录完整拷贝到输出目录下
     *
     * @param inputPath  输入目录
     * @param outputPath 输出目录
     */
    public static void copyFileByHuTool(String inputPath, String outputPath) {
        FileUtil.copy(inputPath, outputPath, false);
    }

}
