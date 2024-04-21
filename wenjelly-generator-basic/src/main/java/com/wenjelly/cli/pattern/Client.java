package com.wenjelly.cli.pattern;

/*
 * @time 2024/3/6 10:37
 * @package com.wenjelly.cli.pattern
 * @project wenjelly-generators
 * @author WenJelly
 */

public class Client {
    public static void main(String[] args) {

        Device tv = new Device("tv");
        TurnOffCommand turnOffCommand = new TurnOffCommand(tv);
        TurnOnCommand turnOnCommand = new TurnOnCommand(tv);
        // 也可以创建多个电视机
        Device stereo = new Device("stereo");
        TurnOffCommand turnOffCommand1 = new TurnOffCommand(stereo);
        TurnOnCommand turnOnCommand1 = new TurnOnCommand(stereo);
        RemoteControl remoteControl = new RemoteControl();
        // 开机
        remoteControl.setCommand(turnOnCommand);
        remoteControl.pressButton();

        // 关机
        remoteControl.setCommand(turnOffCommand);
        remoteControl.pressButton();


    }
}
