package com.stars.command;

import com.stars.MiraiDemo;
import com.stars.entity.Game;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.MemberCommandSenderOnMessage;
import net.mamoe.mirai.console.command.java.JCompositeCommand;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;


public class DuelGameCommand extends JCompositeCommand {

    public static final DuelGameCommand INSTANCE = new DuelGameCommand();

    private DuelGameCommand() {
        super(MiraiDemo.INSTANCE, "duel");
    }

    /**
     * 开始一场决斗♂♂
     * 调用 `/duel <start/开始> <target>` 开始游戏
     * @param sender   元信息
     * @param target    目标
     */
    @SubCommand({"start", "开始"})
    public void start(CommandSender sender, Member target) {
        // 指令发送人，只有群聊可以开始
        if (sender instanceof MemberCommandSenderOnMessage) {
            MemberCommandSenderOnMessage message = (MemberCommandSenderOnMessage) sender;
            Game game;
            if (target != null) {
                // 当前Q群
                Group curGroup = target.getGroup();
                game = DuelGameBuilder.startNewGame(curGroup, message.getUser().getId(), target.getId());
                if (game == null) {
                    sender.sendMessage("当前Q群存在游戏，请等待游戏结束");
                    return;
                }
                sender.sendMessage("决斗开始，参与者为： " + message.getUser().getNameCard() + " 和 " + target.getNameCard());
                game.start();
            } else {
                sender.sendMessage("要两个人才能开始游戏哦，回复[决斗]加入游戏。");
            }
        } else {
            sender.sendMessage("只能在Q群开始游戏哦");
        }
    }


    @SubCommand({"end", "结束"})
    public void end(CommandSender sender) {
        if (sender instanceof MemberCommandSenderOnMessage) {
            MemberCommandSenderOnMessage message = (MemberCommandSenderOnMessage) sender;
            DuelGameBuilder.endGame(message.getGroup());
        }
    }
}
