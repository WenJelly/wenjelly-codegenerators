package com.wenjelly.generatorbackend.job;

/*
 * @time 2024/4/17 10:18
 * @package com.wenjelly.generatorbackend.job
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 */


import cn.hutool.core.util.StrUtil;
import com.wenjelly.generatorbackend.manager.CosManager;
import com.wenjelly.generatorbackend.mapper.GeneratorMapper;
import com.wenjelly.generatorbackend.model.entity.Generator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ClearCosJobHandler {
    @Resource
    private CosManager cosManager;
    @Resource
    private GeneratorMapper generatorMapper;

    /**
     * 每日定时清理任务
     *
     * @throws Exception
     */
    public void clearCosJobHandler() throws Exception {
        log.info("clearCosJobHandler start");
        // 业务逻辑-清理内容
        // 用户上传的模板制作文件("/generator_make_template)
        cosManager.deleteDir("/generator_make_template/");

        // 删除的代码生成器对应的产物包(generator_dist)
        List<Generator> deleteGeneratorList = generatorMapper.listDeletedGenerator();
        List<String> keyList = deleteGeneratorList.stream().map(Generator::getDistPath)
                .filter(StrUtil::isNotBlank)
                // 移除 '/' 前缀
                .map(distPath -> distPath.substring(1))
                .collect(Collectors.toList());

        cosManager.deleteObjects(keyList);
        log.info("clearCosJobHandler end");
    }

}
