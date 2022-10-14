package com.stars.command;

import com.stars.entity.Game;
import com.stars.entity.Player;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;

import java.util.Objects;

/**
 * @program: MiraiDemo
 * @description:
 * @author: HanZiXin
 * @create: 2022-10-14 14:54
 **/
public class GameHelper {

    public static void attack(Game game, Player p) {
        p.isAttack = true;
        Group curGroup = game.getGroup();
        NormalMember member = Objects.requireNonNull(curGroup.get(p.QQ));
        member.sendMessage("请选择攻击的部位（回复数字）：\n1、头部（40HP）；\n2、胸部（30HP）\n3、腿部（20HP）");
    }

    public static void defense(Game game, Player p) {
        p.isAttack = false;
        Group curGroup = game.getGroup();
        NormalMember member = Objects.requireNonNull(curGroup.get(p.QQ));
        member.sendMessage("请选择防御的部位（回复数字）：\n1、头部（40HP）；\n2、胸部（30HP）\n3、腿部（20HP）");
    }

}
