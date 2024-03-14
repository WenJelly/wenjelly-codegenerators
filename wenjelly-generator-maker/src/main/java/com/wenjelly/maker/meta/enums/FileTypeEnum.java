package com.wenjelly.maker.meta.enums;

/*
 * @time 2024/3/10 11:01
 * @package com.wenjelly.maker.meta.enums
 * @project wenjelly-generator-maker
 * @author WenJelly
 */

import lombok.Getter;

@Getter
public enum FileTypeEnum {

    DIR("文件夹","dir"),
    FILE("文件","file"),
    GROUP("文件组","group");

    private final String text;
    private final String value;

    FileTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }
}
