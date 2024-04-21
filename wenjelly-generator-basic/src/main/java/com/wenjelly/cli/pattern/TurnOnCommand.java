package com.wenjelly.cli.pattern;

/*
 * @time 2024/3/6 10:32
 * @package com.wenjelly.cli.pattern
 * @project wenjelly-generators
 * @author WenJelly
 */

public class TurnOnCommand implements Command {

    // 电视机
    private Device device;
    public TurnOnCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        // 电视机的开机
        device.turnOn();
    }
}
