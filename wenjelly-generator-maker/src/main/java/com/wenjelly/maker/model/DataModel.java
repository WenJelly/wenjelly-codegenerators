package com.wenjelly.maker.model;

/*
 * @time 2024/3/3 22:24
 * @package com.wenjelly.model
 * @project wenjelly-generators
 * @author WenJelly
 */

import lombok.Data;

/**
 * 用于生成目标代码的数据模型，通过元信息配置文件生成
 */
@Data
public class DataModel {


    /**
     * 是否生成git文件
     */
    public boolean needGit = true;

    /**
     * 是否生成循环
     */
    public boolean loop = false;

    /**
     * 核心模板文件
     */
    public MainTemplate mainTemplate = new MainTemplate();

    @Data
    public static class MainTemplate {
        /**
         * 作者注释
         */
        public String author = "WenJelly";

        /**
         * 输出信息
         */
        public String outputText = "sum = ";
    }


}
