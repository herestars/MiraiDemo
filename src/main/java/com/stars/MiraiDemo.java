package com.stars;

import com.stars.command.DuelGameBuilder;
import com.stars.command.GameHelper;
import com.stars.command.RobotCommandManager;
import com.stars.constant.Constants;
import com.stars.entity.Game;
import com.stars.entity.Player;
import com.stars.utils.BotUtils;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.utils.MiraiLogger;

/**
 * 主入口
 * 调用 onEnable 开始开发
 */
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
    public void onDisable(){

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
            }
        });


        /* 接受群临时消息事件 **/
        GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessageEvent.class, RobotCommandManager::gameMessage);

        /* 接受好友消息事件 **/
        GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, RobotCommandManager::userMessage);

        /* 机器人登陆事件 **/
        GlobalEventChannel.INSTANCE.subscribeAlways(BotOnlineEvent.class, event -> {
            {
                /* 初始化Bot实例 **/
                BotUtils.initBot(event.getBot());
            }
        });
    }
}