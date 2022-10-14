package com.stars;

import com.stars.command.DuelGameBuilder;
import com.stars.command.RobotCommandManager;
import com.stars.constant.Constants;
import com.stars.entity.Game;
import com.stars.entity.Player;
import com.stars.utils.BotUtils;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.event.events.GroupAwareMessageEvent;
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
    public void onDisable(){

    }

    @Override
    public void onEnable() {
        getLogger().info("Plugin loaded!");
        commandManager = new RobotCommandManager();

        /* 接收群消息事件 **/
        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            {
                int res = commandManager.receiveMessage(Constants.MessageType.GROUP_MESSAGE, event.getTime(),
                        event.getGroup().getId(), event.getSender().getId(), event.getMessage());
//                String fromQQ = Long.toString(event.getSender().getId());
//                String fromName = event.getSenderName();
//                switch (res){
//                    case Constants.ResponseType.Room_Existed:
//                        event.getSubject().sendMessage("决斗房已存在！");
//                    case Constants.ResponseType.Create_Room:
//                        event.getSubject().sendMessage("新的决斗房已由"+fromName+"("+fromQQ+")"+"开启！回复\"出战\"参与决斗吧！");
//                    case Constants.ResponseType.Join_Room:
//                        event.getSubject().sendMessage(fromName+"("+fromQQ+")"+"加入决斗房间成功！");
//                    case Constants.ResponseType.Already_Join:
//                        event.getSubject().sendMessage("你已经加入了喵！健忘啦？");
//                    case Constants.ResponseType.Room_NoExisted:
//                        event.getSubject().sendMessage("啊咧咧？决斗房不存在哦~");
//                    case Constants.ResponseType.Room_Unenough:
//                        event.getSubject().sendMessage("还差一个人才能开始哦~别急喵");
//                    case Constants.ResponseType.Room_Enough:
//                        event.getSubject().sendMessage("决斗房已经满人啦！");
//                    case Constants.ResponseType.StartGame:
//                        event.getSubject().sendMessage("我宣布，决斗开始！");
//                }
                // Call -> AtFunction
                //AtFunction.handleEvent(event);
            }
        });


        GlobalEventChannel.INSTANCE.subscribeAlways(GroupAwareMessageEvent.class, event -> {
            Group group = event.getGroup();
            Game game = DuelGameBuilder.getGame(group);
            if (game == null || !game.isStart()) return;
            Player player = game.getPlayer(event.getSender().getId());
            if (player == null) return;
            String msg = event.getMessage().contentToString().trim();
            if (player.isAttack) {
                game.attack = Integer.parseInt(msg);
            } else {
                game.defense = Integer.parseInt(msg);
            }
            game.step++;
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