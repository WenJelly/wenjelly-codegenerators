/*
 * @time 2024/3/7 12:17
 * @package PACKAGE_NAME
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */

import cn.hutool.core.io.resource.ClassPathResource;

public class Test {

    public static void main(String[] args) {
        // 读取 resources 目录
        ClassPathResource classPathResource = new ClassPathResource("");
        // 模板文件路径
        String inputResourcePath = classPathResource.getAbsolutePath();
        System.out.println(inputResourcePath);
    }

}
