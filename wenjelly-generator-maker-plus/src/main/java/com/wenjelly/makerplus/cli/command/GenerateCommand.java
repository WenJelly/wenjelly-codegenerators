package com.wenjelly.makerplus.cli.command;

/*
 * @time 2024/3/6 10:45
 * @package com.wenjelly.cli.command
 * @project wenjelly-generators
 * @author WenJelly
 */

import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "generate", description = "生成代码",mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable {

    /*
    作者
     */
    @Option(names = {"-a", "--author"}, description = "作者名称", arity = "0..1", interactive = true, prompt = "请输入作者: ", echo = true)
    private String author;

    /*
    输出结果文本
     */
    @Option(names = {"-o", "--outputText"}, description = "输入文本", arity = "0..1", interactive = true, prompt = "请输入输出文本: ", echo = true)
    private String outputText;

    /*
    是否创建while循环
     */
    @Option(names = {"-l", "--loop"}, description = "是否循环", arity = "0..1", interactive = true, prompt = "请输入是否循环: ", echo = true)
    private boolean loop;

    @Override
    public Object call() throws Exception {
//        // 创建数据模型
//        DataModel model = new DataModel();
////        model.setAuthor(author);
////        model.setOutputText(outputText);
////        model.setLoop(loop);
//        // -----------------------------------------------创建数据模型还有更简单的，使用类复制
//        BeanUtil.copyProperties(this,model);
//
//        // 将数据模型传递给模板,创建代码生成器（包括静态与动态）
//        FileGenerator mainGenerator = new FileGenerator();
//        mainGenerator.doGenerate(model);
        return 0;
    }
}
