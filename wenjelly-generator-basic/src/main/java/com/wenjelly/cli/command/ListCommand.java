package com.wenjelly.cli.command;

/*
 * @time 2024/3/6 10:44
 * @package com.wenjelly.cli.command
 * @project wenjelly-generators
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.List;

@Command(name = "list", description = "查看文件列表", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {
    @Override
    public void run() {

        ClassPathResource classPathResource = new ClassPathResource("");
        String absolutePath = classPathResource.getAbsolutePath() + File.separator + "templates/acm-template";

        // hutool工具类，可以遍历目录
        List<File> files = FileUtil.loopFiles(absolutePath);
        for (File f : files) {
            System.out.println(f);
        }
    }
}
