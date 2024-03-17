/*
 * @time 2024/3/7 12:17
 * @package PACKAGE_NAME
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;

import java.io.File;

public class Test {

    public static void main(String[] args) {

        // 输入路径
        String fileInputPath = "src/main/java/com/wenjelly/acm/MainTemplate.java";
        // 输出路径
        String fileOutputPath = fileInputPath + ".ftl";
        System.out.println(fileOutputPath);

    }

}
