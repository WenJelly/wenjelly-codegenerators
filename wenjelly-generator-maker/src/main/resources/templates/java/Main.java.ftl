package ${basePackage}

/*
 * @time ${createTime}
 * @package ${basePackage}.model
 * @project ${name}
 * @author ${author}
*/


import com.wenjelly.maker.cli.CommandExecutor;

public class Main {
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
    }
}
