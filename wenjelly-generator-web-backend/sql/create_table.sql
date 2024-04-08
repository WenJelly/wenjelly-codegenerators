# 数据库初始化

-- 创建库
create database if not exists wenjelly_generator;

-- 切换库
use wenjelly_generator;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_userAccount (userAccount)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 代码生成器表
create table if not exists generator
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(128)                       null comment '名称',
    description text                               null comment '描述',
    basePackage varchar(128)                       null comment '基础包',
    version     varchar(128)                       null comment '版本',
    author      varchar(128)                       null comment '作者',
    tags        varchar(1024)                      null comment '标签列表（json 数组）',
    picture     varchar(256)                       null comment '图片',
    fileConfig  text                               null comment '文件配置（json字符串）',
    modelConfig text                               null comment '模型配置（json字符串）',
    distPath    text                               null comment '代码生成器产物路径',
    status      int      default 0                 not null comment '状态',
    userId      bigint                             not null comment '创建用户 id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '代码生成器' collate = utf8mb4_unicode_ci;

INSERT INTO wenjelly_generator.generator (id, name, description, basePackage, version, author, tags, picture, fileConfig, modelConfig, distPath, status, userId, createTime, updateTime, isDelete) VALUES (100, 'acm-template-pro-generator', 'ACM 示例模板生成器', 'com.wenjelly', '1.0', 'wenjelly', '["Java"]', 'https://code-generator-1325426290.cos.ap-guangzhou.myqcloud.com/demeter-wu-xmas2020-countdown-3.jpg', '{
    "files": [
      {
        "groupKey": "git",
        "groupName": "开源",
        "type": "group",
        "condition": "needGit",
        "files": [
          {
            "inputPath": ".gitignore",
            "outputPath": ".gitignore",
            "type": "file",
            "generateType": "static"
          },
          {
            "inputPath": "README.md",
            "outputPath": "README.md",
            "type": "file",
            "generateType": "static"
          }
        ]
      },
      {
        "inputPath": "src/com/wenjelly/acm/MainTemplate.java.ftl",
        "outputPath": "src/com/wenjelly/acm/MainTemplate.java",
        "type": "file",
        "generateType": "dynamic"
      }
    ]
  }', '{"models":[{"fieldName":"needGit","type":"boolean","description":"是否生成 .gitignore 文件","defaultValue":true},{"fieldName":"loop","type":"boolean","description":"是否生成循环","defaultValue":false,"abbr":"l"},{"type":"MainTemplate","description":"用于生成核心模板文件","groupKey":"mainTemplate","groupName":"核心模板","models":[{"fieldName":"author","type":"String","description":"作者注释","defaultValue":"wenjelly","abbr":"a"},{"fieldName":"outputText","type":"String","description":"输出信息","defaultValue":"sum = ","abbr":"o"}],"condition":"loop"}]}', '/generator_dist/1738875515482562562/kLbG2yGh-acm-template-pro-generator.zip', 0, 1738875515482562562, '2024-01-06 23:00:17', '2024-01-08 18:50:12', 0);