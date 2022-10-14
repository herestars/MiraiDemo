package com.stars.entity;

public class Player {
    public Long QQ;
    public Long fromGroup;
    public boolean isAttack;
    public double HP = 120;
    public double HeadAttack = 40;
    public double ChestAttack = 30;
    public double LegAttack = 20;
    public double HeadDodge = 0.25;
    public double ChestDodge = 0.15;
    public double LegDodge = 0.10;

    public Player(Long QQ, Long fromGroup) {
        this.QQ = QQ;
        this.fromGroup = fromGroup;
    }

    public Player() {}

}
