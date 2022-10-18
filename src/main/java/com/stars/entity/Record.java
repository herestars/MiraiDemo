package com.stars.entity;

import java.util.Comparator;

public class Record {
    private Long QQ;
    private String NickName;
    private Long Win;
    private Long Lose;
    private Long Draw;

    public Record(Long QQ,String NickName,Long Win,Long Lose,Long Draw){
        this.QQ = QQ;
        this.NickName = NickName;
        this.Win = Win;
        this.Lose = Lose;
        this.Draw = Draw;
    }

    public static Comparator<Record> cmp = new Comparator<Record>() {
        @Override
        public int compare(Record o1, Record o2) {
            if(o1.Win != o2.Win) {
                return o2.Win.intValue() - o1.Win.intValue();
            } else if (o1.Draw != o2.Draw) {
                return o2.Draw.intValue() - o2.Draw.intValue();
            } else {
                return o2.Lose.intValue() - o1.Lose.intValue();
            }
        }
    };

    public Long getQQ() {
        return QQ;
    }

    public void setQQ(Long QQ) {
        this.QQ = QQ;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public Long getWin() {
        return Win;
    }

    public void setWin(Long win) {
        Win = win;
    }

    public Long getLose() {
        return Lose;
    }

    public void setLose(Long lose) {
        Lose = lose;
    }

    public Long getDraw() {
        return Draw;
    }

    public void setDraw(Long draw) {
        Draw = draw;
    }

    public void AddLose() {
        Lose ++;
    }

    public void AddWin() {
        Win ++;
    }

    public void AddDraw() {
        Draw ++;
    }

    @Override
    public String toString() {
        return "Record{" +
                "QQ=" + QQ +
                ", NickName='" + NickName + '\'' +
                ", Win=" + Win +
                ", Lose=" + Lose +
                '}';
    }

}
