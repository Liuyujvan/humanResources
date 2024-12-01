package com.nowcoder.humanresources.entity;

public class AwardPunishment {
    private String id; // 工号, primary key
    private String name; // 姓名
    private String award; // 奖励
    private String punishment; // 惩罚
    private String awardTime; // 奖励时间
    private String punishmentTime; // 惩罚时间

    // Constructor
    public AwardPunishment(String id, String name, String award, String punishment, String awardTime, String punishmentTime) {
        this.id = id;
        this.name = name;
        this.award = award;
        this.punishment = punishment;
        this.awardTime = awardTime;
        this.punishmentTime = punishmentTime;
    }

    public AwardPunishment() {

    }

    // Getters and Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getPunishment() {
        return punishment;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
    }

    public String getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(String awardTime) {
        this.awardTime = awardTime;
    }

    public String getPunishmentTime() {
        return punishmentTime;
    }

    public void setPunishmentTime(String punishmentTime) {
        this.punishmentTime = punishmentTime;
    }

    // Override toString method for better display
    @Override
    public String toString() {
        return "AwardPunishmentInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", award='" + award + '\'' +
                ", punishment='" + punishment + '\'' +
                ", awardTime='" + awardTime + '\'' +
                ", punishmentTime='" + punishmentTime + '\'' +
                '}';
    }
}


