package ${basePackage}.cli.command;

/*
 * @time ${createTime}
 * @package ${basePackage}.cli.command
 * @project ${name}
 * @author ${author}
 */


import cn.hutool.core.util.ReflectUtil;
import com.wenjelly.model.DataModel;
import picocli.CommandLine.Command;

import java.lang.reflect.Field;

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
