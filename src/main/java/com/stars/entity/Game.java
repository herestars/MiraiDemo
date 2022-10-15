package com.stars.entity;

import com.stars.command.DuelGameBuilder;
import com.stars.command.GameHelper;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game {
    private List<Player> players;
    private Group group;
    private boolean isStart = false;
    private int round = 1;
    public boolean isOver;

    private Game(Group group) {
        this.group = group;
        this.players = new ArrayList<>();
    }

    public static Game GameInit(Group group, Member... playersQQ) {
        Game game = new Game(group);
        if (playersQQ != null && playersQQ.length != 0) {
            for (Member qq : playersQQ) {
                game.addPlayer(qq);
            }
        }
        return game;
    }

    public Player getPlayer(Long playersQQ) {
        for (Player player : players) {
            if (Objects.equals(playersQQ, player.QQ.getId())) return player;
        }
        return null;
    }

    public void addPlayer(Member playersQQ) {
        players.add(new Player(playersQQ, this.group));
    }

    public boolean isStart() {
        return isStart;
    }

    /**
     * 开始游戏
     */
    public void start() {
        isStart = true;
        Player p1 = players.get(0), p2 = players.get(1);
        String n1 = Objects.requireNonNull(group.get(p1.QQ)).getNameCard(),
                n2 = Objects.requireNonNull(group.get(p2.QQ)).getNameCard();
        boolean flag = new Random().nextBoolean();
        while (p1.HP > 0 && p2.HP > 0) {
            if (isOver) return;
            group.sendMessage("第" + round + "回合开始\n" +
                    "玩家1：" + n1 + "(" + p1.QQ.getId() + ")" + "剩余血量为 " + p1.HP +
                    " HP\n玩家2：" + n2 + "(" + p2.QQ.getId() + ")" + "剩余血量为 " + p2.HP + " HP");
            if(p1.IsFreeze) group.sendMessage("本回合 "+n1+" 因上回合攻击失败玉玉中！无法行动喵！");
            if (!GameHelper.duel(p1, p2)) return;
            round++;
        }
        if (p1.HP < 0 && p2.HP < 0) {
            group.sendMessage("双方同归于尽！本次决斗没有赢家！建议不服再来一场喵！");
        } else if (p1.HP > 0) {
            group.sendMessage("恭喜 " + n1 + " 获胜，剩余血量："+p1.HP+"建议 "+n2+" 不服再来一场喵！");
        } else {
            group.sendMessage("恭喜 " + n2 + " 获胜，建议不服再来一场");
        }
        isStart = false;
        DuelGameBuilder.endGame(group);
    }


    public Group getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return players.get(0).QQ.getNick() + " 和 " + players.get(1).QQ.getNick();
    }
}
