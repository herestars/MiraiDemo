package com.stars.command;

import com.stars.constant.Constants;
import com.stars.entity.Game;
import com.stars.entity.Player;
import com.stars.utils.BotUtils;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupAwareMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.Objects;

public class RobotCommandManager {

    // 机器人收到的信息，无论私聊还是群聊，都调用这个函数执行
    public void receiveMessage(int msgType, int time, long fromGroup, long fromQQ, MessageChain messageChain) {

        // Get MessageChain to Text.
        String msg = messageChain.contentToString();
        msg = msg.trim();
        // [!] 验证接收到的东西是否为文本，防止对方发送 文件过来，导致报错
        if (msg.length() == 0) {
            return;
        }

        Group curGroup = BotUtils.getBot().getGroup(fromGroup);
        if (msgType == Constants.MessageType.GROUP_MESSAGE && "决斗".equals(msg)) {
            if (curGroup == null) return;
            Game game = DuelGameBuilder.getGame(curGroup);
            NormalMember member = curGroup.get(fromQQ);
            if (member == null) return;
            if (game == null) {
                DuelGameBuilder.startNewGame(curGroup, member);
                curGroup.sendMessage("游戏准备中，目前已有玩家：" + member.getNick());
                return;
            }
            if (game.getPlayer(fromQQ) != null) {
                curGroup.sendMessage("你已经在游戏里了！");
                return;
            }
            if (game.isStart()) {
                curGroup.sendMessage("游戏已经开始了，等待下一次游戏吧~");
                return;
            }
            game.addPlayer(member);
            curGroup.sendMessage("决斗开始，参与人为：" + game);
            game.start();
        } else if (msgType == Constants.MessageType.GROUP_MESSAGE && "结束".equals(msg)) {
            DuelGameBuilder.endGame(curGroup);
            Objects.requireNonNull(curGroup).sendMessage("当前游戏强制结束");
        }
    }

    public static void gameMessage(GroupAwareMessageEvent event) {
        Group group = event.getGroup();
        Game game = DuelGameBuilder.getGame(group);
        // 如果没有游戏，就退出
        game(game, event.getSender(), event.getMessage(), event);
    }

    public static void userMessage(FriendMessageEvent event) {
        long qq = event.getSender().getId();
        Game game = DuelGameBuilder.findGameByUser(qq);
        // 如果没有游戏，就退出
        game(game, event.getSender(), event.getMessage(), event);
    }

    private static void game(Game game, User sender, MessageChain message, MessageEvent event) {
        if (game == null || !game.isStart()) return;
        Player player = game.getPlayer(sender.getId());
        if (player == null) return;
        if (player.move >= 0 && player.move <= 6) return;
        int move = -1;
        try {
            move = Integer.parseInt(message.contentToString().trim());
        } catch (Exception e) {
            sender.sendMessage("请输入数字喵！");
        }
        if (move < 1 || move > 6) {
            sender.sendMessage("请输入合法的数字喵！");
            GameHelper.move(player);
            return;
        }
        player.move = move;
        System.out.println(player.QQ.getId() + " 做出了选择 " + move);
        game.getGroup().sendMessage(sender.getNick() + " 准备好了");
    }
}
