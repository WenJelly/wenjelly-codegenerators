package com.wenjelly.backend.service;

/*
 * @time 2024/3/24 14:55
 * @package com.wenjelly.backend.service
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 * 帖子服务
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wenjelly.backend.model.dto.generator.GeneratorAddRequest;
import com.wenjelly.backend.model.dto.generator.GeneratorQueryRequest;
import com.wenjelly.backend.model.entity.Generator;
import com.wenjelly.backend.model.vo.GeneratorVO;

import javax.servlet.http.HttpServletRequest;


public interface GeneratorService extends IService<Generator> {

    /**
     * 校验
     *
     * @param generator
     * @param add
     */
    void validGenerator(Generator generator, boolean add);

    /**
     * 获取查询条件
     *
     * @param generatorQueryRequest
     * @return
     */
    QueryWrapper<Generator> getQueryWrapper(GeneratorQueryRequest generatorQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param Generator
     * @param request
     * @return
     */
    GeneratorVO getGeneratorVO(Generator Generator, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param GeneratorPage
     * @param request
     * @return
     */
    Page<GeneratorVO> getGeneratorVOPage(Page<Generator> GeneratorPage, HttpServletRequest request);
}
