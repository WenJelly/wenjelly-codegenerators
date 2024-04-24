package com.wenjelly.makerplus.generator;

/*
 * @time 2024/3/9 10:28
 * @package com.wenjelly.makerplus.generator
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 将jar包封装成脚本，方便执行
 */
public class ScriptGenerator {

    /**
     * 封装脚本
     *
     * @param outputPath 脚本输出位置
     * @param jarPath    jar包所在位置
     */
    public static void doGenerate(String outputPath, String jarPath) {
        // 直接写入脚本文件
        // linux
        StringBuilder sb = new StringBuilder();
        sb.append("#!bin/bash").append("\n");
        sb.append(String.format("java -jar %s \"$@\"", jarPath)).append("\n");

        FileUtil.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8), outputPath);

        // 添加可执行权限，在windows上注释掉，因为会抛操作不支持
//        Set<PosixFilePermission> permissions  = PosixFilePermissions.fromString("rwxrwxrwx");
//        try {
//            Files.setPosixFilePermissions(Paths.get(outputPath),permissions);
//        } catch (IOException e) {
//        }

        // windows
        sb = new StringBuilder();
        sb.append("@echo off").append("\n");
        sb.append(String.format("java -jar %s %%*", jarPath)).append("\n");
        FileUtil.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8), outputPath + ".bat");
    }

    public static void main(String[] args) throws IOException {
        String outputPath = System.getProperty("user.dir") + File.separator + "generator";
        doGenerate(outputPath, "");
    }

}
