package com.wenjelly.springboottemplate.model.dto.user;

/*
 * @time 2024/3/24 15:02
 * @package com.wenjelly.springboottemplate.model.dto.user
 * @project spring-boot-template
 * @author WenJelly
 * 用户更新个人信息请求
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateMyRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 简介
     */
    private String userProfile;
}
