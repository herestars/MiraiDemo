package com.stars.utils;

public class Player {
    public String QQ;
    public String fromGroup;
    private static double HP = 120;
    private static double HeadAttack = 40;
    private static double ChestAttack = 30;
    private static double LegAttack = 20;
    private static double HeadDodge = 0.25;
    private static double ChestDodge = 0.15;
    private static double LegDodge = 0.10;

    public Player(String QQ, String fromGroup) {
        this.QQ = QQ;
        this.fromGroup = fromGroup;
    }

    public static double getHP() {
        return HP;
    }

    public static void setHP(double HP) {
        Player.HP = HP;
    }

    public static double getHeadAttack() {
        return HeadAttack;
    }

    public static void setHeadAttack(double headAttack) {
        HeadAttack = headAttack;
    }

    public static double getChestAttack() {
        return ChestAttack;
    }

    public static void setChestAttack(double chestAttack) {
        ChestAttack = chestAttack;
    }

    public static double getLegAttack() {
        return LegAttack;
    }

    public static void setLegAttack(double legAttack) {
        LegAttack = legAttack;
    }

    public static double getHeadDodge() {
        return HeadDodge;
    }

    public static void setHeadDodge(double headDodge) {
        HeadDodge = headDodge;
    }

    public static double getChestDodge() {
        return ChestDodge;
    }

    public static void setChestDodge(double chestDodge) {
        ChestDodge = chestDodge;
    }

    public static double getLegDodge() {
        return LegDodge;
    }

    public static void setLegDodge(double legDodge) {
        LegDodge = legDodge;
    }
}
