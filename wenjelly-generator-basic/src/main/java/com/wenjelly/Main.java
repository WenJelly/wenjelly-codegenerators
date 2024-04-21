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
        CommandExecutor commandExecutor = new CommandExecutor();
        args = new String[] {"generate","-a","wenjelly","-o","你好","-l","false"};
        commandExecutor.doExecute(args);
    }
}
