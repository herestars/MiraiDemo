package com.stars.command;

import com.stars.entity.Game;
import com.stars.entity.Player;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;

import java.util.Random;

/**
 * @program: MiraiDemo
 * @description:
 * @author: HanZiXin
 * @create: 2022-10-14 14:54
 **/
public class GameHelper {


    /**
     * 选择攻击或者防御
     *
     * @param p 玩家
     */
    public static void move(Player p) {
        Member member = p.QQ;
        if (p.IsFreeze) {
            member.sendMessage("玉玉了（悲，本回合什么都不能做）");
            p.move = 0;
            p.IsFreeze = false;
            return;
        }
        member.sendMessage("请选择攻击的部位（回复数字）：\n1、头部（" + p.HeadAttack + "HP)\n2、胸部（" + p.ChestAttack + "HP）\n3、腿部（" + p.LegAttack + "HP）" +
                "\n或者选择防守的部位：\n4、头部\n5、胸部\n6、腿部\n防守成功将完全抵挡伤害并冻结对手一回合。");
    }

    // attackLogic
    // 玩家攻击逻辑计算函数
    // self.move 就是本回合执行的操作
    public static void attackLogic(Player self, Player target) {
        Random r = new Random();
        // 当前游戏所在的q群
        Group group = self.fromGroup;
        String n1 = self.QQ.getNick();
        String n2 = target.QQ.getNick();
        if (self.move == 1) {
            if (r.nextInt(Constants.GameVal.RAND_RANGE) >= target.HeadDodge * Constants.GameVal.RAND_RANGE && target.move != 4) {
                group.sendMessage(self.QQ.getNick()+"选择了攻击头部："+"爆头！" + n2 + " - " + self.HeadAttack + " HP");
                target.HP -= self.HeadAttack;
            } else if (target.move == 4) {
                group.sendMessage(self.QQ.getNick()+"选择了攻击头部："+"爆头！什么！是防弹头盔！\n" + n1 + " 本次攻击失败，下一回合将受到由挫败感打击带来的冻结惩罚（无法行动）");
                self.IsFreeze = true;
            } else {
                group.sendMessage(self.QQ.getNick()+"选择了攻击头部："+self.QQ.getNick()+"，你他娘的打偏了!");
            }
        } else if (self.move == 2) {
            if (r.nextInt(Constants.GameVal.RAND_RANGE) >= target.ChestDodge * Constants.GameVal.RAND_RANGE && target.move != 5) {
                group.sendMessage(self.QQ.getNick()+"选择了攻击胸部："+"袭胸！" + n2 + " - " + self.ChestAttack + " HP");
                target.HP -= self.ChestAttack;
            } else if (target.move == 5) {
                group.sendMessage(self.QQ.getNick()+"选择了攻击胸部："+"袭胸！什么！是男的！\n" + n1 + " 本次攻击失败，下一回合将受到由挫败感打击带来的冻结惩罚（无法行动）");
                self.IsFreeze = true;
            } else {
                group.sendMessage(self.QQ.getNick()+"选择了攻击胸部："+self.QQ.getNick()+"，你他娘的打偏了!");
            }
        } else if (self.move == 3) {
            if (r.nextInt(Constants.GameVal.RAND_RANGE) >= target.LegDodge * Constants.GameVal.RAND_RANGE && target.move != 6) {
                group.sendMessage(self.QQ.getNick()+"选择了攻击腿部："+"我一个滑铲！" + n2 + " - " + self.LegAttack + " HP");
                target.HP -= self.LegAttack;
            } else if (target.move == 6) {
                group.sendMessage(self.QQ.getNick()+"选择了攻击腿部："+"什么！他居然会跳！\n" + n1 + " 本次攻击失败，下一回合将受到由挫败感打击带来的冻结惩罚（无法行动）");
                self.IsFreeze = true;
            } else {
                group.sendMessage(self.QQ.getNick()+"选择了攻击腿部："+self.QQ.getNick()+"，你他娘的打偏了!");
            }
        }
    }

    /**
     * 决斗开始！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
     *
     * @param p1 玩家1
     * @param p2 玩家2
     */
    public static boolean duel(Player p1, Player p2) {
        move(p1);
        move(p2);
        Group group = p1.fromGroup;
        long start = System.currentTimeMillis();
        // 循环等待两名玩家做出选择
        while (p1.move == -1 || p2.move == -1) {
            if (DuelGameBuilder.getGame(group).isOver) return false;
            if (System.currentTimeMillis() - start > 60 * 1000) {
                group.sendMessage("回复超时，本次游戏结束");
                DuelGameBuilder.endGame(group);
                return false;
            }
        }
        // 两名玩家做出的选择
        int m1 = p1.move, m2 = p2.move;
        if (m1 > 3 && m2 > 3) {
            // 两个玩家都选择防御，无事发生
            group.sendMessage("本回合战报：两名勇者明明很强却都选择了防御。。。");
        } else {
            // 两名选手都选择了进攻，勇者胜
            attackLogic(p1, p2);
            attackLogic(p2, p1);
        }
        // 回合结束
        p1.move = p2.move = -1;
        return true;
    }

}
