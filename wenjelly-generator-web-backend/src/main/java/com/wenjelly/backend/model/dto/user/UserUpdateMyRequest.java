package com.wenjelly.backend.model.dto.user;

/*
 * @time 2024/3/24 15:02
 * @package com.wenjelly.backend.model.dto.user
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 * 用户更新个人信息请求
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateMyRequest implements Serializable {

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

    private static final long serialVersionUID = 1L;
}
