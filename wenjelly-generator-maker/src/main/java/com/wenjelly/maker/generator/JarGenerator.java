package com.wenjelly.maker.generator;

/*
 * @time 2024/3/9 9:20
 * @package com.wenjelly.maker.generator
 * @project wenjelly-generator-maker
 * @author WenJelly
 */

import java.io.*;

/**
 * 将生成好的目标生成器打包
 */
public class JarGenerator {

    public static void main(String[] args) throws IOException, InterruptedException {
        doGenerate("D:\\Development\\IDEAJavaProjects\\wenjelly-generators\\wenjelly-generator-maker\\generated\\acm-template-generator");
    }

    public static void doGenerate(String projectDir) throws IOException, InterruptedException {
        // 清理之前的构建并打包
        // 注意不同的操作系统，执行的指令不同
        String winMavenCommand = "mvn.cmd clean package -DskipTests=true";
        String otherMavenCommand = "mvn clean package -DskipTests=true";
        String mavenCommand = winMavenCommand;

        // 这里一定要拆分，以空格拆分
        ProcessBuilder processBuilder = new ProcessBuilder(mavenCommand.split(" "));
        processBuilder.directory(new File(projectDir));

        Process process = processBuilder.start();

        // 读取命令的输出

        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        // 等待指令执行完成
        int exitCode = process.waitFor();
        System.out.println("命令执行结束，退出码：" + exitCode);
    }
}
