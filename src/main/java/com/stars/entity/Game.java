package com.stars.entity;

import com.stars.command.DuelGameBuilder;
import com.stars.command.GameHelper;
import net.mamoe.mirai.contact.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game {
    private List<Player> players;
    private Group group;
    private boolean isStart = false;
    private int round = 1;
    public int attack;
    public int defense;
    public volatile int step;

    private Game(Group group) {
        this.group = group;
        this.players = new ArrayList<>();
    }

    public static Game GameInit(Group group, Long... playersQQ){
        Game game = new Game(group);
        if (playersQQ != null && playersQQ.length != 0) {
            for (Long qq : playersQQ) {
                game.addPlayer(qq);
            }
        }
        return game;
    }

    public Player getPlayer(Long playersQQ) {
        for (Player player : players) {
            if (Objects.equals(playersQQ, player.QQ)) return player;
        }
        return null;
    }

    public void addPlayer(Long playersQQ) {
        players.add(new Player(playersQQ, this.group.getId()));
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
            group.sendMessage("第" + round + "回合开始");
            if (flag) {
                GameHelper.attack(this, p1);
                GameHelper.defense(this, p2);
            } else {
                GameHelper.attack(this, p2);
                GameHelper.defense(this, p1);
            }
            long start = System.currentTimeMillis();
            while (step != 2) {
                if (System.currentTimeMillis() - start > 60 * 1000) {
                    group.sendMessage("回复超时，本次游戏结束");
                    DuelGameBuilder.endGame(group);
                    return;
                }
            }
            step = 0;
            Random r = new Random();
            if (attack == 1) {
                if (r.nextInt(100) >= 40 && defense != 1) {
                    group.sendMessage("爆头！" + n2 + " HP - 40");
                    p2.HP -= 40;
                } else if (defense == 1) {
                    group.sendMessage("爆头！什么！是防弹头盔，" + n1 + " 本次攻击失败。");
                } else {
                    group.sendMessage("柱子，你他娘的打偏了");
                }
            } else if (attack == 2) {
                if (r.nextInt(100) >= 30 && defense != 2) {
                    group.sendMessage("袭胸！" + n2 + " HP - 30");
                    p2.HP -= 30;
                } else if (defense == 2) {
                    group.sendMessage("袭胸！什么！是男的，" + n1 + " 本次攻击失败。");
                } else {
                    group.sendMessage("柱子，你他娘的打偏了");
                }
            } else {
                if (r.nextInt(100) >= 20 && defense != 3) {
                    group.sendMessage("我一个滑铲！" + n2 + " HP - 30");
                    p2.HP -= 20;
                } else if (defense == 3) {
                    group.sendMessage("什么！他居然会跳，" + n1 + " 本次攻击失败。");
                } else {
                    group.sendMessage("柱子，你他娘的打偏了");
                }
            }
            round++;
        }
        if (p1.HP > 0) {
            group.sendMessage("恭喜 " + n1 + " 获胜，建议不服再来一场");
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
        return "" + group.get(players.get(0).QQ).getNameCard() + " 和 " + group.get(players.get(1).QQ).getNameCard();
    }
}
