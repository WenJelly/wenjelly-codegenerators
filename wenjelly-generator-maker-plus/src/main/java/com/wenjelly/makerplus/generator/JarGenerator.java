package com.wenjelly.makerplus.generator;

/*
 * @time 2024/3/9 9:20
 * @package com.wenjelly.makerplus.generator
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */

import java.io.*;

/**
 * 用于打包
 */
public class JarGenerator {

    /**
     * 构建jar包
     *
     * @param projectDir 项目文件夹位置
     * @throws IOException          IOE异常
     * @throws InterruptedException 构建异常
     */
    public static void doGenerate(String projectDir) throws IOException, InterruptedException {
        // 清理之前的构建并打包
        // 注意不同的操作系统，执行的指令不同
        // Windows指令
        String winMavenCommand = "mvn.cmd clean package -DskipTests=true";
        // 其他操作系统指令
        String otherMavenCommand = "mvn clean package -DskipTests=true";
        // 如果运行在服务器上，则需要将 winMavenCommand 修改成 otherMavenCommand
        String mavenCommand = winMavenCommand;

        // 这里一定要拆分，以空格拆分
        ProcessBuilder processBuilder = new ProcessBuilder(mavenCommand.split(" "));
        processBuilder.directory(new File(projectDir));
        // 执行指令
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
