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

import java.util.concurrent.Callable;

@Command(name = "generate", description = "生成代码",mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable {

    <#list modelConfig.models as modelInfo>
        @Option(names = {<#if modelInfo.abbr??> "-${modelInfo.abbr}" , </#if>"--${modelInfo.fieldName}"}, <#if modelInfo.description??>description = "${modelInfo.description}",</#if> arity = "0..1", interactive = true,  echo = true)
        private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c} </#if>;
    </#list>

    @Override
    public Object call() throws Exception {
        // 创建数据模型
        DataModel model = new DataModel();
//        model.setAuthor(author);
//        model.setOutputText(outputText);
//        model.setLoop(loop);
        // -----------------------------------------------创建数据模型还有更简单的，使用类复制
        BeanUtil.copyProperties(this,model);

        // 将数据模型传递给模板,创建代码生成器（包括静态与动态）
        MainFileGenerator mainGenerator = new MainFileGenerator();
        mainGenerator.doGenerate(model);
        return 0;
    }
}
