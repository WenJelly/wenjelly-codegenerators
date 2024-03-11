package com.wenjelly.maker.meta.enums;

/*
 * @time 2024/3/10 11:07
 * @package com.wenjelly.maker.meta.enums
 * @project wenjelly-generator-maker
 * @author WenJelly
 */

import lombok.Getter;

/**
 * 模型类型枚举
 */
@Getter
public enum ModelTypeEnum {

    STRING("字符串", "String"),
    BOOLEAN("布尔", "boolean");

    private final String text;
    private final String value;

    ModelTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }
}
