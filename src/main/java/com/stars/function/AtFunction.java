package com.stars.function;

import com.stars.utils.BotUtils;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;

public class AtFunction {

    public static void handleEvent(GroupMessageEvent event) {


        // Has @Bot ?
        if (event.getMessage().contains(new At(BotUtils.getBot().getId()))) {
            long fromGroup = event.getGroup().getId();
            long fromQQ = event.getSender().getId();
            String receiveMsg = event.getMessage().contentToString();
            event.getSender().sendMessage("1111");
//            // Get Answer And SendMsg.
//            String sendMsg = QingYunKe_API.getAnswer(receiveMsg);
//            MessageManager.sendMessageBySituation(fromGroup, fromQQ, sendMsg);
        }

    }
}