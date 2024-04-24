package com.wenjelly.maker.cli.command;

/*
 * @time 2024/3/6 10:44
 * @package com.wenjelly.cli.command
 * @project wenjelly-generators
 * @author WenJelly
 */

import cn.hutool.core.util.ReflectUtil;
import com.wenjelly.maker.model.DataModel;
import picocli.CommandLine.Command;

import java.lang.reflect.Field;

/**
 * 查看配置指令模板
 */
@Command(name = "config", description = "查看配置", mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable {
    @Override
    public void run() {
        // 获取MainTemplateConfig的所有字段
        Field[] fields = ReflectUtil.getFields(DataModel.class);
        // 打印字段信息
        for (Field field : fields) {
            System.out.println("字段类型：" + field.getType() + "   字段名称： " + field.getName());
            System.out.println("---");
        }

    }
}
