package com.wenjelly.model;

/*
 * @time 2024/3/3 22:24
 * @package com.wenjelly.model
 * @project wenjelly-generators
 * @author WenJelly
 */

import lombok.Data;
@Data
public class MainTemplateConfig {

    /*
    作者
     */
    private String author = "wenjelly";

    /*
    输出结果文本
     */
    private String outputText = "sum = ";

    /*
    是否创建while循环
     */
    private boolean loop = true;

}
