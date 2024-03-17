package com.wenjelly.makerplus.template.enums;

/*
 * @time 2024/3/17 16:03
 * @package com.wenjelly.makerplus.template.enums
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * 文件过滤范围枚举
 */
@Getter
public enum FileFilterRangeEnum {

    FILE_NAME("文件名称", "fileName"),
    FILE_CONTENT("文件内容", "fileValue");

    private String text;
    private String value;

    FileFilterRangeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据value获得枚举
     */

    public static FileFilterRangeEnum getEnumsByValue(String value) {
        if (ObjectUtil.isEmpty(value)) {
            return null;
        }
        for (FileFilterRangeEnum anEnum : FileFilterRangeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
