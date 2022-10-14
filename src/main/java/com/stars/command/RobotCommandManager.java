package com.stars.command;

import com.stars.constant.Constants;
import com.stars.function.Game;
import com.stars.utils.GameData;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.ArrayList;

public class RobotCommandManager {

    // 机器人收到的信息，无论私聊还是群聊，都调用这个函数执行
    public int receiveMessage(int msgType, int time, long fromGroup, long fromQQ, MessageChain messageChain) {

        // Get MessageChain to Text.
        String msg = messageChain.contentToString();
        // [!] 验证接收到的东西是否为文本，防止对方发送 文件过来，导致报错
        if (msg.length() == 0) {
            return Constants.ResponseType.No_Reply;
        }

        if (msg.equals("发起决斗")) {
            String f_group = Long.toString(fromGroup);
            if (GameData.DuelRoom.containsKey(f_group)){
                return Constants.ResponseType.Room_Existed;
            } else {
                ArrayList<String> room = new ArrayList<String>();
                room.add(Long.toString(fromQQ));
                GameData.DuelRoom.put(f_group,room);
                return Constants.ResponseType.Create_Room;
            }
        } else if (msg.equals("出战")) {
            String f_group = Long.toString(fromGroup);
            if (GameData.DuelRoom.containsKey(f_group)){
                ArrayList<String> arr = GameData.DuelRoom.get(f_group);
                if(arr.size() < GameData.RoomSize) {
                    String f_QQ = Long.toString(fromQQ);
                    if (arr.get(0).equals(f_QQ)) {
                        return Constants.ResponseType.Already_Join;
                    }
                    GameData.DuelRoom.get(f_group).add(f_QQ);
                    return Constants.ResponseType.Join_Room;
                } else {
                    return Constants.ResponseType.Room_Enough;
                }
            } else {
                return Constants.ResponseType.Room_NoExisted;
            }

        } else if (msg.equals("决斗开始")) {
            String f_group = Long.toString(fromGroup);
            if (GameData.DuelRoom.containsKey(f_group)){
                ArrayList<String> playersQQ = GameData.DuelRoom.get(f_group);
                if(playersQQ.size() == GameData.RoomSize) {
                    Game.GameInit(f_group,playersQQ);
                    return Constants.ResponseType.StartGame;
                } else {
                    return Constants.ResponseType.Room_Unenough;
                }
            } else {
                return Constants.ResponseType.Room_NoExisted;
            }
        }

        // 循环遍历每个已注册的命令
//        for (RobotCommand command : commands) {
//
//            // 判断用户输入的是什么命令
//            if (!command.isThisCommand(msg)) {
//                continue;
//            }
//
//            // 判断该用户是否可以执行该命令
//            if (command.runCheckUp(msgType, time, fromGroup, fromQQ, messageChain)) {
//                // 正式执行命令
//                command.runCommand(msgType, time, fromGroup, fromQQ, messageChain);
//            }
//
//            return;
//        }

    }
}
