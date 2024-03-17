/*
 * @time 2024/3/7 12:17
 * @package PACKAGE_NAME
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;

import java.io.File;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        String path = System.getProperty("user.dir");


        List<File> files = FileUtil.loopFiles(path);
        for (File file :files) {
            System.out.println(file.getName());
        }


    }

}
