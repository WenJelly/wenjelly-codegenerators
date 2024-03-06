package com.wenjelly.cli.example;

/*
 * @time 2024/3/5 10:55
 * @package com.wenjelly.cli.example
 * @project wenjelly-generators
 * @author WenJelly
 */


import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

public class Login implements Callable<Integer> {
    @Option(names = {"-u", "--user"}, description = "user name")
    String user;

    // interactive 参数设置为 true，表示该选项支持交互式输入，prompt参数用于提示输入语,echo为false时，说明用户输入的信息不可见
    @Option(names = {"-p", "--password"}, description = "user password", arity = "0..1", interactive = true, prompt = "请输入密码: ", echo = false)
    String password;

    // 在实现一个检验密码
    @Option(names = {"-cp", "--checkPassword"}, description = "check password", arity = "0..1", interactive = true, prompt = "请再次输入密码: ", echo = false)
    String checkPassword;

    /**
     * 等同于Run方法
     *
     * @return 返回值，一般用于返回结果的整数形式
     * @throws Exception 异常
     */
    @Override
    public Integer call() throws Exception {
        System.out.println("username = " + user);
        System.out.println("password = " + password);
        System.out.println("checkPassword = " + checkPassword);
        return 0;
    }

    public static void main(String[] args) {

        // 判断用户输入的指令是否有-p
        boolean flag = false;
        for (String arg : args) {
            if ("-p".equals(arg)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            // 新建临时字符串数组
            String[] tmp = new String[args.length + 1];
            for (int i = 0; i < args.length; i++) {
                tmp[i] = args[i];
            }
            tmp[args.length] = "-p";

            args = tmp;
        }

        // 会提示让你输入密码
//        new CommandLine(new Login()).execute("-u", "wenjelly");
        new CommandLine(new Login()).execute(args);
    }
}
