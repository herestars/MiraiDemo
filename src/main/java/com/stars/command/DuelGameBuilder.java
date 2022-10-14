package com.stars.command;

import com.stars.entity.Game;
import net.mamoe.mirai.contact.Group;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: MiraiDemo
 * @description: 保存全局游戏局数
 * @author: HanZiXin
 * @create: 2022-10-14 11:26
 **/
public class DuelGameBuilder {

    private static Map<Group, Game> gameMap = new HashMap<>();

    public static Game startNewGame(Group group, Long... players) {
        if (gameMap.containsKey(group)) return null;
        Game game = Game.GameInit(group, players);
        gameMap.put(group, game);
        return game;
    }

    public static void endGame(Group group) {
        gameMap.remove(group);
    }

    public static Game getGame(Group group) {
        return gameMap.get(group);
    }

}
