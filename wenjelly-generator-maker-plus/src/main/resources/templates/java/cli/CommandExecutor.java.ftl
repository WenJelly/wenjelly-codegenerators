package ${basePackage}.cli;

import com.wenjelly.cli.command.ConfigCommand;
import com.wenjelly.cli.command.ListCommand;
import com.wenjelly.cli.command.GenerateCommand;
import com.wenjelly.cli.command.JsonGenerateCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/*
 * @time ${createTime}
 * @package ${basePackage}.cli
 * @project ${name}
 * @author ${author}
 */

@Command(name = "${name}", version = "${name}${version}", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {

    private CommandLine commandLine;

    {
        commandLine = new CommandLine(this)
                .addSubcommand(new ConfigCommand())
                .addSubcommand(new GenerateCommand())
                .addSubcommand(new ListCommand())
                .addSubcommand(new JsonGenerateCommand());
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
