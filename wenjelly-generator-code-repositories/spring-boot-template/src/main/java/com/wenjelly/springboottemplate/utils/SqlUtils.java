package com.wenjelly.springboottemplate.utils;

/*
 * @time 2024/3/24 14:54
 * @package com.wenjelly.springboottemplate.utils
 * @project spring-boot-template
 * @author WenJelly
 * SQL 工具
 */

import org.apache.commons.lang3.StringUtils;

public class SqlUtils {

    /**
     * 校验排序字段是否合法（防止 SQL 注入）
     *
     * @param sortField
     * @return
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
