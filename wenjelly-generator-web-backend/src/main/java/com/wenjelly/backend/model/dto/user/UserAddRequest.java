package com.wenjelly.backend.model.dto.user;

/*
 * @time 2024/3/24 15:01
 * @package com.wenjelly.backend.model.dto.user
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 * 用户创建请求
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
