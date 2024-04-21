package com.wenjelly.springboottemplate.model.entity;

/*
 * @time 2024/3/24 15:03
 * @package com.wenjelly.springboottemplate.model.entity
 * @project spring-boot-template
 * @author WenJelly
 * 帖子收藏
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "post_favour")
@Data
public class PostFavour implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 帖子 id
     */
    private Long postId;
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
}
