package com.wenjelly.springboottemplate.model.dto.user;

/*
 * @time 2024/3/24 15:01
 * @package com.wenjelly.springboottemplate.model.dto.user
 * @project spring-boot-template
 * @author WenJelly
 * 用户创建请求
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;
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
}
