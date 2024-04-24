package com.wenjelly.makerplus.template;

/*
 * @time 2024/3/21 14:43
 * @package com.wenjelly.makerplus.template
 * @project wenjelly-generator-maker-plus
 * @author WenJelly
 */

import cn.hutool.core.util.StrUtil;
import com.wenjelly.makerplus.meta.Meta;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 源代码模板制作工具类
 */
public class TemplateMakerUtils {

    /**
     * 从未分组文件中移除组内的同名文件
     * 1.获取到所有的分组
     * 2.获取到所有分组内的文件列表
     * 3.获取所有分组内的文件输入路径集合
     * 4.利用上述集合，移除所有输入路径在集合中的外层文件
     *
     * @param fileInfoList
     * @return
     */
    public static List<Meta.FileConfigBean.FileInfo> removeGroupFilesFromRoot(List<Meta.FileConfigBean.FileInfo> fileInfoList) {

        // 获取到所有的分组
        List<Meta.FileConfigBean.FileInfo> groupFileInfoList = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
                .collect(Collectors.toList());
        System.out.println(groupFileInfoList);

        // 获取到所有分组内的文件列表
        List<Meta.FileConfigBean.FileInfo> groupInnerFileInfoList = groupFileInfoList.stream()
                .flatMap(fileInfo -> fileInfo.getFiles().stream())
                .collect(Collectors.toList());

        // 获取所有分组内文件输入路径集合
        List<String> fileInputPathSet = groupInnerFileInfoList.stream()
                .map(Meta.FileConfigBean.FileInfo::getInputPath)
                .collect(Collectors.toList());

        // 移除所有名称在 set 中的外层文件
        return fileInfoList.stream()
                .filter(fileInfo -> !fileInputPathSet.contains(fileInfo.getInputPath()))
                .collect(Collectors.toList());

    }
}
