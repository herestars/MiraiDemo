package com.stars;

import com.stars.command.RobotCommandManager;
import com.stars.constant.Constants;
import com.stars.function.AtFunction;
import com.stars.utils.BotUtils;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public final class MiraiDemo extends JavaPlugin {
    public static final MiraiDemo INSTANCE = new MiraiDemo();
    private static RobotCommandManager commandManager;

    private MiraiDemo() {
        super(new JvmPluginDescriptionBuilder("com.stars.mirai", "0.1.0")
                .name("MiraiDemo")
                .author("HanZiXin")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("Plugin loaded!");
        commandManager = new RobotCommandManager();
        /* 接收群消息事件 **/
        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            {
                commandManager.receiveMessage(Constants.MessageType.GROUP_MESSAGE, event.getTime(),
                        event.getGroup().getId(), event.getSender().getId(), event.getMessage());

                // Call -> AtFunction
                AtFunction.handleEvent(event);
            }
        });

        /* 机器人登陆事件 **/
        GlobalEventChannel.INSTANCE.subscribeAlways(BotOnlineEvent.class, event -> {
            {
                /* 初始化Bot实例 **/
                BotUtils.initBot(event.getBot());
            }
        });
    }
}