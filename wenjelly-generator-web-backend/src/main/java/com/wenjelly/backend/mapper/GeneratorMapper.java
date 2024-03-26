package com.wenjelly.backend.mapper;

/*
 * @time 2024/3/24 14:48
 * @package com.wenjelly.backend.mapper
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 * 帖子数据库操作
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wenjelly.backend.model.entity.Generator;

import java.util.Date;
import java.util.List;

public interface GeneratorMapper extends BaseMapper<Generator> {

    /**
     * 查询帖子列表（包括已被删除的数据）
     */
    List<Generator> listGeneratorWithDelete(Date minUpdateTime);

}
