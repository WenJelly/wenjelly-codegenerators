package com.wenjelly.cli.pattern;

/*
 * @time 2024/3/6 10:34
 * @package com.wenjelly.cli.pattern
 * @project wenjelly-generators
 * @author WenJelly
 */

public class RemoteControl {

    private static Command command;
    // 传入用户按下了什么按钮
    public void setCommand(Command command) {
        this.command = command;
    }

    // 按下按钮，调用对应按钮的功能
    public void pressButton() {
        command.execute();
    }
}
