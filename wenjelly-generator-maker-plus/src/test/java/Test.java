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

        String property = System.getProperty("user.dir");
        String path = property + File.separator + ".output/nihao";

        String text = "hello";

        FileUtil.writeUtf8String(text, path);

    }

}
