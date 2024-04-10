package com.wenjelly.makerplus.template;

/*
 * @time 2024/3/17 16:10
 * @package com.wenjelly.makerplus.template
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;
import com.wenjelly.makerplus.template.enums.FileFilterRangeEnum;
import com.wenjelly.makerplus.template.enums.FileFilterRuleEnum;
import com.wenjelly.makerplus.template.model.FileFilterConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFilter {

    /**
     * 过滤单个文件
     *
     * @param filterConfigList
     * @param file
     * @return
     */
    public static boolean doSingleFileFilter(File file, List<FileFilterConfig> filterConfigList) {

        String fileName = file.getName();
        String fileContent = FileUtil.readUtf8String(file);

        boolean result = true;
        if (filterConfigList == null) {
            return true;
        }

        for (FileFilterConfig fileFilterConfig : filterConfigList) {
            String range = fileFilterConfig.getRange();
            String rule = fileFilterConfig.getRule();
            String value = fileFilterConfig.getValue();

            FileFilterRangeEnum fileFilterRangeEnum = FileFilterRangeEnum.getEnumsByValue(range);
            if (fileFilterRangeEnum == null) {
                continue;
            }

            // 要过滤的内容
            String content = fileName;
            switch (fileFilterRangeEnum) {
                case FILE_NAME:
                    content = fileName;
                    break;
                case FILE_CONTENT:
                    content = fileContent;
                    break;
                default:
            }

            // 过滤的规则
            FileFilterRuleEnum fileFilterRuleEnum = FileFilterRuleEnum.getEnumByValue(rule);
            if (fileFilterRuleEnum == null) {
                continue;
            }

            switch (fileFilterRuleEnum) {
                case CONTAINS:
                    result = content.contains(value);
                    break;
                case STARTS_WITH:
                    result = content.startsWith(value);
                    break;
                case ENDS_WITH:
                    result = content.endsWith(value);
                    break;
                case REGEX:
                    result = content.matches(value);
                    break;
                case EQUALS:
                    result = content.equals(value);
                    break;
                default:
            }

            // 有一个不满足
            if (!result) {
                return false;
            }

        }

        // 都满足
        return true;
    }

    public static List<File> doFilter(String filePath, List<FileFilterConfig> filterConfigList) {

        // 创建一个数组用于返回
        ArrayList<File> fileArrayList = new ArrayList<>();

        // 先判断文件路径是文件夹还是文件
        if (FileUtil.isDirectory(filePath)) {
            // 如果是文件夹的话，先遍历，得到所有的文件数组
            List<File> fileList = FileUtil.loopFiles(filePath);
            // 遍历文件数组，对每个文件进行一个过滤
            for (File file : fileList) {
                if (doSingleFileFilter(file, filterConfigList)) {
                    fileArrayList.add(file);
                }
            }
        } else {
            // 说明为单个文件，直接调用单个文件过滤方法
            if (doSingleFileFilter(new File(filePath), filterConfigList)) {
                fileArrayList.add(new File(filePath));
            }
        }
        return fileArrayList;

        /**
         * 高级写法，但是我还不会= =
         *     // 根据路径获取所有文件
         *     List<File> fileList = FileUtil.loopFiles(filePath);
         *     return fileList.stream()
         *             .filter(file -> doSingleFileFilter(fileFilterConfigList, file))
         *             .collect(Collectors.toList());
         */

    }
}
