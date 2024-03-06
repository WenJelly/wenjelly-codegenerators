package com.wenjelly;

/*
 * @time 2024/3/6 19:52
 * @package com.wenjelly
 * @project wenjelly-generators
 * @author WenJelly
 */

import com.wenjelly.cli.CommandExecutor;

public class Main {
    public static void main(String[] args) {
//        args = new String[]{"generate", "-l", "-a", "-o"};
//        args = new String[]{"config"};
//        args = new String[]{"list"};
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
    }
}
