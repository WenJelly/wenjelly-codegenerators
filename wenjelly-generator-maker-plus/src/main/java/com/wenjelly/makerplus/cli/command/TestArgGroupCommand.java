package com.wenjelly.makerplus.cli.command;

/*
 * @time 2024/3/6 10:45
 * @package com.wenjelly.cli.command
 * @project wenjelly-generators
 * @author WenJelly
 */



import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;


import java.util.concurrent.Callable;

@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class TestArgGroupCommand implements Callable {

    @Option(names = {"-needGit"}, description = "是否生成git", arity = "0..1", interactive = true, prompt = "请输入是否生成git相关文件", echo = true)
    private boolean needGit;

    /*
    是否创建while循环
     */
    @Option(names = {"-l", "--loop"}, description = "是否循环", arity = "0..1", interactive = true, prompt = "请输入是否循环: ", echo = true)
    private boolean loop;


//    static DataModel.MainTemplate mainTemplate = new DataModel.MainTemplate();

    @Override
    public Object call() throws Exception {
        System.out.println(needGit);
        System.out.println(loop);
        if (true){
            System.out.println("请输入配置");
            CommandLine commandLine = new CommandLine(MainTemplate.class);
            commandLine.execute("-o","-a");
//            System.out.println(mainTemplate);
        }
        return 0;
    }

    @Data
    @Command(name = "mainTemplate")
    static class MainTemplate implements Runnable{
        @Option(names = {"-a", "--author"}, arity = "0..1", interactive = true, prompt = "请输入作者: ", echo = true)
        private String author = "wenjelly";

        @Option(names = {"-o", "--outputText"}, arity = "0..1", interactive = true, prompt = "请输入输出文本: ", echo = true)
        private String outputText = "sum";

        @Override
        public void run() {
//            mainTemplate.author = author;
//            mainTemplate.outputText = outputText;
        }
    }

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(TestArgGroupCommand.class);
        commandLine.execute("-l");
    }

}

