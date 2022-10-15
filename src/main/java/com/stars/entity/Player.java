package com.stars.entity;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;


/**
 * 玩家对象
 */
public class Player {
    /**
     * QQ 玩家QQ对象
     */
    public Member QQ;
    /**
     * 玩家所在的群主
     */
    public Group fromGroup;
    /**
     * 每轮玩家选择的操作
     * 每轮结束后重新赋值为-1
     */
    public int move = -1;
    /**
     * 玩家初始血量
     */
    public double HP = 120;
    public double HeadAttack = 40;
    public double ChestAttack = 30;
    public double LegAttack = 20;
    public double HeadDodge = 0.25;
    public double ChestDodge = 0.15;
    public double LegDodge = 0.10;

    public Player(Member QQ, Group fromGroup) {
        this.QQ = QQ;
        this.fromGroup = fromGroup;
    }

    public Player() {}
}
