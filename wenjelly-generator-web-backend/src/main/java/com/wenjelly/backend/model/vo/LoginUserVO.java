package com.wenjelly.backend.model.vo;

/*
 * @time 2024/3/24 15:03
 * @package com.wenjelly.backend.model.vo
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 * 已登录用户视图（脱敏）
 */

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginUserVO implements Serializable {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
