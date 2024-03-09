package com.wenjelly.maker.cli;

import com.wenjelly.maker.cli.command.ConfigCommand;
import com.wenjelly.maker.cli.command.GenerateCommand;
import com.wenjelly.maker.cli.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/*
 * @time 2024/3/6 10:55
 * @package com.wenjelly.cli
 * @project wenjelly-generators
 * @author WenJelly
 */
@Command(name = "wenjelly", version = "wenjelly 1.0", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {

    private CommandLine commandLine;

    {
        commandLine = new CommandLine(this)
                .addSubcommand(new ConfigCommand())
                .addSubcommand(new GenerateCommand())
                .addSubcommand(new ListCommand());
    }

    @Override
    public void run() {
        // 不输入子命令时，给出友好提示
        System.out.println("请输入具体命令，或者输入 --help 查看命令提示");
    }

    public Integer doExecute(String[] args) {
        return commandLine.execute(args);
    }
}
