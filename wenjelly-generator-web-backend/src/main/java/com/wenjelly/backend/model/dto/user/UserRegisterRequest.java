package com.wenjelly.backend.model.dto.user;

/*
 * @time 2024/3/24 15:02
 * @package com.wenjelly.backend.model.dto.user
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 * 用户注册请求体
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
