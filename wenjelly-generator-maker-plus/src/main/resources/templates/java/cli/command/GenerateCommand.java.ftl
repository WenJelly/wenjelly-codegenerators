package ${basePackage}.cli.command;

/*
* @time ${createTime}
* @package ${basePackage}.cli.command
* @project ${name}
* @author ${author}
*/


import cn.hutool.core.bean.BeanUtil;
import com.wenjelly.generator.MainFileGenerator;
import com.wenjelly.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Command(name = "generate", description = "生成代码",mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable {


<#list modelConfig.models as modelInfo>

    <#if modelInfo.groupKey??>
        static DataModel.${modelInfo.type} ${modelInfo.groupKey} = new DataModel.${modelInfo.type}();

        @Data
        @Command(name = "${modelInfo.groupKey}")
        static class ${modelInfo.type}Command implements Runnable {
        <#list modelInfo.models as modelInfo>
            @Option(names = {<#if modelInfo.abbr??> "-${modelInfo.abbr}" , </#if>"--${modelInfo.fieldName}"}, <#if modelInfo.description??>description = "${modelInfo.description}",</#if> arity = "0..1", interactive = true,  echo = true)
            private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c} </#if>;
        </#list>
        @Override
        public void run() {
        <#list modelInfo.models as fieldInfo>
            ${modelInfo.groupKey}.${fieldInfo.fieldName} = ${fieldInfo.fieldName};
        </#list>
        }
        }
    <#else >
        @Option(names = {<#if modelInfo.abbr??> "-${modelInfo.abbr}" , </#if>"--${modelInfo.fieldName}"}, <#if modelInfo.description??>description = "${modelInfo.description}",</#if> arity = "0..1", interactive = true,  echo = true)
        private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c} </#if>;
    </#if >
</#list>

@Override
public Integer call() throws Exception {
<#list modelConfig.models as modelInfo>
    <#if modelInfo.groupName??>
        <#if modelInfo.condition??>
            if (${modelInfo.condition}) {
            System.out.println("输入${modelInfo.groupName}配置：");
            CommandLine ${modelInfo.condition}CommandLine = new CommandLine(${modelInfo.type}Command.class);
            ${modelInfo.condition}CommandLine.execute(${modelInfo.allArgsStr});
            }
        <#else >
            System.out.println("输入${modelInfo.groupName}配置：");
            CommandLine commandLine = new CommandLine(${modelInfo.type}Command.class);
            commandLine.execute(${modelInfo.allArgsStr});
        </#if>
    </#if>
</#list>
<#-- 填充数据模型对象 -->
DataModel dataModel = new DataModel();
BeanUtil.copyProperties(this, dataModel);
<#list modelConfig.models as modelInfo>
    <#if modelInfo.groupKey??>
        dataModel.${modelInfo.groupKey} = ${modelInfo.groupKey};
    </#if>
</#list>
MainFileGenerator.doGenerate(dataModel);
return 0;
}
}
