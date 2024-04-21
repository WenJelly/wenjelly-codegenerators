package com.wenjelly.cli.pattern;

/*
 * @time 2024/3/6 10:32
 * @package com.wenjelly.cli.pattern
 * @project wenjelly-generators
 * @author WenJelly
 */

public class TurnOffCommand implements Command {

    // 电视机
    private Device device;
    public TurnOffCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        // 电视机的关闭
        device.turnOff();
    }
}
