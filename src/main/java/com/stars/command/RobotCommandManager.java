package com.stars.command;

import net.mamoe.mirai.message.data.MessageChain;

/**
 * @program: MiraiDemo
 * @description:
 * @author: HanZiXin
 * @create: 2022-10-13 14:32
 **/
public class RobotCommandManager {

    // 机器人收到的信息，无论私聊还是群聊，都调用这个函数执行
    public void receiveMessage(int msgType, int time, long fromGroup, long fromQQ, MessageChain messageChain) {

        // Get MessageChain to Text.
        String msg = messageChain.contentToString();

        // [!] 验证接收到的东西是否为文本，防止对方发送 文件过来，导致报错
        if (msg.length() == 0) {
            return;
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
