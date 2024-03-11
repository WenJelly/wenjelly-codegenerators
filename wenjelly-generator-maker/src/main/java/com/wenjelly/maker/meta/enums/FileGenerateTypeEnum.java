package com.wenjelly.maker.meta.enums;

/*
 * @time 2024/3/10 11:06
 * @package com.wenjelly.maker.meta.enums
 * @project wenjelly-generator-maker
 * @author WenJelly
 */

import lombok.Getter;

/**
 * 文件生成类型
 */
@Getter
public enum FileGenerateTypeEnum {

    DYNAMIC("动态", "dynamic"),
    STATIC("静态", "static");

    private final String text;
    private final String value;

    FileGenerateTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }
}
