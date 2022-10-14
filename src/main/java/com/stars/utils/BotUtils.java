package com.stars.utils;

import net.mamoe.mirai.Bot;

public class BotUtils {

    private static Bot BOT;

    public static void initBot(Bot bot) {
        BOT = bot;
    }

    public static Bot getBot() {
        return BOT;
    }

}
