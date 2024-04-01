package com.wenjelly.springboottemplate.controller;

/*
 * @time 2024/3/24 14:24
 * @package com.wenjelly.springboottemplate.controller
 * @project spring-boot-template
 * @author WenJelly
 * 帖子点赞接口
 */

import com.wenjelly.springboottemplate.common.BaseResponse;
import com.wenjelly.springboottemplate.common.ErrorCode;
import com.wenjelly.springboottemplate.common.ResultUtils;
import com.wenjelly.springboottemplate.exception.BusinessException;
import com.wenjelly.springboottemplate.model.dto.postthumb.PostThumbAddRequest;
import com.wenjelly.springboottemplate.model.entity.User;
import com.wenjelly.springboottemplate.service.PostThumbService;
import com.wenjelly.springboottemplate.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/post_thumb")
@Slf4j
public class PostThumbController {

    @Resource
    private PostThumbService postThumbService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param postThumbAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Integer> doThumb(@RequestBody PostThumbAddRequest postThumbAddRequest,
                                         HttpServletRequest request) {
        if (postThumbAddRequest == null || postThumbAddRequest.getPostId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long postId = postThumbAddRequest.getPostId();
        int result = postThumbService.doPostThumb(postId, loginUser);
        return ResultUtils.success(result);
    }

}
