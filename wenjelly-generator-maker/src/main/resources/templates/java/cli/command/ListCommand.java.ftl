package ${basePackage}.cli.command;

/*
 * @time ${createTime}
 * @package ${basePackage}.cli.command
 * @project ${name}
 * @author ${author}
 */

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.List;

@Command(name = "list", description = "查看文件列表", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {
    @Override
    public void run() {

        String inputPath = "${fileConfig.inputRootPath}";
        // hutool工具类，可以遍历目录
        List<File> files = FileUtil.loopFiles(inputPath);
        for (File f : files) {
            System.out.println(f);
        }
    }
}