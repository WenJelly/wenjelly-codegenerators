package com.wenjelly.springboottemplate.wxmp;

/*
 * @time 2024/3/24 14:52
 * @package com.wenjelly.springboottemplate.wxmp
 * @project spring-boot-template
 * @author WenJelly
 * 微信公众号路由
 */

import com.wenjelly.springboottemplate.wxmp.handler.EventHandler;
import com.wenjelly.springboottemplate.wxmp.handler.MessageHandler;
import com.wenjelly.springboottemplate.wxmp.handler.SubscribeHandler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class WxMpMsgRouter {

    @Resource
    private WxMpService wxMpService;

    @Resource
    private EventHandler eventHandler;

    @Resource
    private MessageHandler messageHandler;

    @Resource
    private SubscribeHandler subscribeHandler;

    @Bean
    public WxMpMessageRouter getWxMsgRouter() {
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        // 消息
        router.rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.TEXT)
                .handler(messageHandler)
                .end();
        // 关注
        router.rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(subscribeHandler)
                .end();
        // 点击按钮
        router.rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.CLICK)
                .eventKey(WxMpConstant.CLICK_MENU_KEY)
                .handler(eventHandler)
                .end();
        return router;
    }
}
