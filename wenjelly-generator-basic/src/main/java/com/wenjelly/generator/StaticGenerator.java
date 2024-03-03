package com.wenjelly.generator;

/*
 * @time 2024/3/3 10:06
 * @package com.wenjelly.generator
 * @project wenjelly-generators
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * 该类用于生成静态代码
 */


public class StaticGenerator {

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        // 输入目录,这里使用的File.separator的原因是，由于不同的操作系统，它的分隔符不一定一样，而 separator 可以自动识别当前操作系统的分隔符
        String inputPath = projectPath + File.separator + "wenjelly-generator-demo-projects" + File.separator + "acm-template";
        // 输出目录
        String outputPath = projectPath + File.separator + "wenjelly-generator-basic/src/main/resources/templatesout";
        copyFileByHuTool(inputPath, outputPath);
    }

    /**
     * 用于拷贝文件，使用HuTool工具类，会将输入目录完整拷贝到输出目录下
     * @param inputPath  输入目录
     * @param outputPath 输出目录
     */
    public static void copyFileByHuTool(String inputPath, String outputPath) {
        FileUtil.copy(inputPath, outputPath, false);
    }

    /**
     * 递归拷贝文件（递归实现，会将输入目录完整拷贝到输出目录下）
     * @param inputPath 输入文件的路径
     * @param outputPath 目标文件的路径
     */
    public static void copyFilesByRecursive(String inputPath, String outputPath) {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);
        try {
            copyFileByRecursive(inputFile, outputFile);
        } catch (Exception e) {
            System.err.println("文件复制失败");
            e.printStackTrace();
        }
    }

    /**
     *
     * 文件 A => 目录 B，则文件 A 放在目录 B 下
     * 文件 A => 文件 B，则文件 A 覆盖文件 B
     * 目录 A => 目录 B，则目录 A 放在目录 B 下
     * 核心思路：先创建目录，然后遍历目录内的文件，依次复制
     * @param inputFile 输入文件
     * @param outputFile 目标文件
     * @throws IOException IOE异常
     */
    private static void copyFileByRecursive(File inputFile,File outputFile) throws IOException{
        // 区分是文件还是目录
        if (inputFile.isDirectory()) {
            System.out.println(inputFile.getName());
            File destOutputFile = new File(outputFile, inputFile.getName());
            // 如果是目录，首先创建目标目录
            if (!destOutputFile.exists()) {
                destOutputFile.mkdirs();
            }
            // 获取目录下的所有文件和子目录
            File[] files = inputFile.listFiles();
            // 无子文件，直接结束
            if (ArrayUtil.isEmpty(files)) {
                return;
            }
            for (File file : files) {
                // 递归拷贝下一层文件
                copyFileByRecursive(file, destOutputFile);
            }
        }else {
            // 是文件，直接复制到目标目录下
            Path destPath = outputFile.toPath().resolve(inputFile.getName());
            Files.copy(inputFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
        }

    }

}
