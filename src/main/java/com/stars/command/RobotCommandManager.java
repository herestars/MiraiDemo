package com.stars.command;

import com.stars.constant.Constants;
import com.stars.entity.Game;
import com.stars.utils.BotUtils;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.data.MessageChain;

public class RobotCommandManager {

    // 机器人收到的信息，无论私聊还是群聊，都调用这个函数执行
    public int receiveMessage(int msgType, int time, long fromGroup, long fromQQ, MessageChain messageChain) {

        // Get MessageChain to Text.
        String msg = messageChain.contentToString();
        msg = msg.trim();
        // [!] 验证接收到的东西是否为文本，防止对方发送 文件过来，导致报错
        if (msg.length() == 0) {
            return Constants.ResponseType.No_Reply;
        }

        Group curGroup = BotUtils.getBot().getGroup(fromGroup);
        if (msgType == Constants.MessageType.GROUP_MESSAGE && "决斗".equals(msg)) {
            if (curGroup == null) return Constants.ResponseType.Room_NoExisted;
            Game game = DuelGameBuilder.getGame(curGroup);
            if (game == null) {
                DuelGameBuilder.startNewGame(curGroup, fromQQ);
                NormalMember member = curGroup.get(fromQQ);
                if (member == null) return Constants.ResponseType.Room_Enough;
                curGroup.sendMessage("游戏准备中，目前已有玩家：" + member.getNameCard());
                return Constants.ResponseType.StartGame;
            }
            if (game.isStart()) {
                curGroup.sendMessage("游戏已经开始了，等待下一次游戏吧~");
                return Constants.ResponseType.No_Reply;
            }
            game.addPlayer(fromQQ);
            curGroup.sendMessage("决斗开始，参与人为：" + game);
            game.start();
        }


//        if (msg.equals("发起决斗")) {
//            String f_group = Long.toString(fromGroup);
//            if (GameData.DuelRoom.containsKey(f_group)){
//                return Constants.ResponseType.Room_Existed;
//            } else {
//                ArrayList<String> room = new ArrayList<String>();
//                room.add(Long.toString(fromQQ));
//                GameData.DuelRoom.put(f_group,room);
//                return Constants.ResponseType.Create_Room;
//            }
//        } else if (msg.equals("出战")) {
//            String f_group = Long.toString(fromGroup);
//            if (GameData.DuelRoom.containsKey(f_group)){
//                ArrayList<String> arr = GameData.DuelRoom.get(f_group);
//                if(arr.size() < GameData.RoomSize) {
//                    String f_QQ = Long.toString(fromQQ);
//                    if (arr.get(0).equals(f_QQ)) {
//                        return Constants.ResponseType.Already_Join;
//                    }
//                    GameData.DuelRoom.get(f_group).add(f_QQ);
//                    return Constants.ResponseType.Join_Room;
//                } else {
//                    return Constants.ResponseType.Room_Enough;
//                }
//            } else {
//                return Constants.ResponseType.Room_NoExisted;
//            }
//
//        } else if (msg.equals("决斗开始")) {
//            String f_group = Long.toString(fromGroup);
//            if (GameData.DuelRoom.containsKey(f_group)){
//                ArrayList<String> playersQQ = GameData.DuelRoom.get(f_group);
//                if(playersQQ.size() == GameData.RoomSize) {
//                    return Constants.ResponseType.StartGame;
//                } else {
//                    return Constants.ResponseType.Room_Unenough;
//                }
//            } else {
//                return Constants.ResponseType.Room_NoExisted;
//            }
//        }
        return Constants.ResponseType.Join_Room;

    }
}
