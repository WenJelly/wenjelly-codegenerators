package com.wenjelly.makerplus.meta;

/*
 * @time 2024/3/8 11:02
 * @package com.wenjelly.makerplus.meta
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

/**
 * 用于管理Meta的生成，为了节约内存，这里采用双检索单例设计模式
 */
public class MetaManager {

    private static volatile  Meta meta;

    private  MetaManager() {
        // 私有构造函数，防止外部实例化
    }

    public static Meta getMetaObject() {

        if(meta == null){
            // 加锁
            synchronized (MetaManager.class){
                // 如果还为空
                if(meta == null){
                    meta = initMeta();
                }
            }
        }

        return meta;
    }

    private static Meta initMeta() {
        // 通过hutool类读取meta.json文件并转化为字符串
        String metaJson = ResourceUtil.readUtf8Str("springboot-init-meta.json");
        // 将json字符串转化为对象并进行赋值
        Meta newMeta = JSONUtil.toBean(metaJson, Meta.class);
        // 校验和处理默认值
        MetaValidator.doValidAndFill(newMeta);
        return newMeta;
    }

}
