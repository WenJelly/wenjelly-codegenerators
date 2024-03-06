package com.wenjelly.cli.command;

/*
 * @time 2024/3/6 10:44
 * @package com.wenjelly.cli.command
 * @project wenjelly-generators
 * @author WenJelly
 */


import cn.hutool.Hutool;
import cn.hutool.core.io.FileUtil;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.List;

@Command(name = "list", description = "查看文件列表", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {
    @Override
    public void run() {
        // 获取用户当前的目录
        String property = System.getProperty("user.dir");

        File parentFile = new File(property);

        String absolutePath = new File(parentFile, "/wenjelly-generator-demo-projects/acm-template").getAbsolutePath();
        // hutool工具类，可以遍历目录
        List<File> files = FileUtil.loopFiles(absolutePath);
        for (File f : files) {
            System.out.println(f);
        }
    }
}
