package com.stars.utils;

import com.stars.entity.Player;
import net.mamoe.mirai.console.data.AutoSavePluginData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class GameData {
    public static int RoomSize = 2;
    public static HashMap<String, ArrayList<String>> DuelRoom;
    public static HashMap<String, Player> PlayerList;
}
