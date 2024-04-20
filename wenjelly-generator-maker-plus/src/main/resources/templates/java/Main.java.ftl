package ${basePackage};

/*
 * @time ${createTime}
 * @package ${basePackage}
 * @project ${name}
 * @author ${author}
 */

import com.wenjelly.cli.CommandExecutor;

public class Main {

    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
    }
}