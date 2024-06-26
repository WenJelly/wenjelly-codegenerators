package com.wenjelly.generatorbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wenjelly.generatorbackend.model.entity.Generator;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 生成器数据库操作
 */
public interface GeneratorMapper extends BaseMapper<Generator> {

    /**
     * 查询生成器列表（包括已被删除的数据）
     */
    List<Generator> listPostWithDelete(Date minUpdateTime);

    @Select("SELECT id,distPath FROM generator Where isDelete = 1")
    List<Generator> listDeletedGenerator();

}




