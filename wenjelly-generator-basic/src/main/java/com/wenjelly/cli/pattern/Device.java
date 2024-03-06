package com.wenjelly.cli.pattern;

/*
 * @time 2024/3/6 10:33
 * @package com.wenjelly.cli.pattern
 * @project wenjelly-generators
 * @author WenJelly
 */

public class Device {

    private String name;

    public Device(String name) {
        this.name = name;
    }


    public void turnOn() {
        System.out.println("电视机打开了");
    }

    public void turnOff() {
        System.out.println("电视机关闭了");
    }

}
