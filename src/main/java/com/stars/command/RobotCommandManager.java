package com.stars.command;

import com.stars.constant.Constants;
import com.stars.entity.Game;
import com.stars.entity.Player;
import com.stars.entity.Record;
import com.stars.utils.BotUtils;
import com.stars.utils.PlayerRecord;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupAwareMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.Objects;
import java.util.PriorityQueue;

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
        Game game = DuelGameBuilder.getGame(curGroup);
        if (msgType == Constants.MessageType.GROUP_MESSAGE && ("决斗".equals(msg) || "duel".equals(msg))) {
            if (curGroup == null) return;
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
        } else if (msgType == Constants.MessageType.GROUP_MESSAGE && ("决斗结束".equals(msg) || "duel end".equals(msg))) {
            if (game == null || game.getPlayer(fromQQ) == null) {
                curGroup.sendMessage("你不在游戏中，无法结束。");
                return;
            }
            DuelGameBuilder.endGame(curGroup);
            Objects.requireNonNull(curGroup).sendMessage("当前游戏强制结束");
        } else if (msgType == Constants.MessageType.GROUP_MESSAGE && ("决斗帮助".equals(msg)|| "duel help".equals(msg))) {
            curGroup.sendMessage("决斗游戏 说明：\n" +
                    "决斗游戏为 1V1 的 PVP 回合制小游戏，双方通过与机器人的私聊选择每回合所做出的行动，" +
                    "若对手 HP 降至 0 及以下则获胜。若双方在同一回合后同时 HP 降至 0 及以下则平局。\n" +
                    "指令：\n" +
                    "决斗 / duel： 在群内发起一场决斗或者加入一场决斗\n" +
                    "决斗结束 / duel end： 结束群里正在进行或等待的决斗\n" +
                    "决斗排行 / duel rank：查看决斗排行 Top10 \n"+
                    "决斗个人战绩/ duel me：查看个人战绩信息\n" +
                    "决斗帮助 / duel help： 查看决斗游戏说明\n" +
                    "注意：若群禁止临时对话需要加机器人好友哦！");
        } else if (msgType == Constants.MessageType.GROUP_MESSAGE && ("决斗排行".equals(msg)|| "duel rank".equals(msg))) {
            PriorityQueue<Record> rank = new PriorityQueue<>(Record.cmp);
            for( Record r : PlayerRecord.recordMap.values()) {
                if(!r.getQQ().equals((long)-1))
                rank.add(r);
            }
            if(rank.size() == 0) {
                curGroup.sendMessage("排行榜暂无数据喵~");
                return;
            }
            StringBuilder rankMsg = new StringBuilder("决斗游戏排行榜（Top 10）：\n");
            for(int i=1;i<=10;i++) {
                Record r = rank.poll();
                String str = i +". "+r.getNickName()+"("+r.getQQ()+")："+" "+r.getWin()+"胜 "+r.getLose()+"负 "+r.getDraw()+"平\n";
                rankMsg.append(str);
            }
            curGroup.sendMessage(rankMsg.toString());
        } else if (msgType == Constants.MessageType.GROUP_MESSAGE && ("决斗个人战绩".equals(msg)|| "duel me".equals(msg))) {
            if(!PlayerRecord.recordMap.containsKey(fromQQ)) {
                curGroup.sendMessage("对不起，您还没有决斗记录哦！快玩一局吧~");
            }
            Record r = PlayerRecord.recordMap.get(fromQQ);
            double Sum =(double) (r.getWin()+r.getDraw()+r.getLose());
            double WinRate = Sum/(double)r.getWin();
            String Msg = r.getNickName()+"("+r.getQQ()+")您的战绩为："+" "+r.getWin()+"胜 "+r.getLose()+"负 "+r.getDraw()+"平，总局数："
            + Sum + "，胜率为 "+WinRate+"\n";
            curGroup.sendMessage(Msg);
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
