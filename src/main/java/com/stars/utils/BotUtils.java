package com.stars.utils;

import net.mamoe.mirai.Bot;

/**
 * @program: MiraiDemo
 * @description:
 * @author: HanZiXin
 * @create: 2022-10-13 14:43
 **/
public class BotUtils {

    private static Bot BOT;

    public static void initBot(Bot bot) {
        BOT = bot;
    }

    public static Bot getBot() {
        return BOT;
    }

}
