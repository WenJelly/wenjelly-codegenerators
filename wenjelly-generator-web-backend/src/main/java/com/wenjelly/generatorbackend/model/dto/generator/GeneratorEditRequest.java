package com.wenjelly.generatorbackend.model.dto.generator;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 编辑请求
 */
@Data
public class GeneratorEditRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 基础包
     */
    private String basePackage;
    /**
     * 版本
     */
    private String version;
    /**
     * 作者
     */
    private String author;
    /**
     * 图片
     */
    private String picture;
    /**
     * 文件配置（json字符串）
     */
    private String fileConfig;
    /**
     * 模型配置（json字符串）
     */
    private String modelConfig;
    /**
     * 代码生成器产物路径
     */
    private String distPath;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建用户 id
     */
    private Long userId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 标签列表
     */
    private List<String> tags;
}