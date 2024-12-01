package com.nowcoder.humanresources.entity;

import java.util.Date;

public class Teacher {
    private String id; // 工号, primary key
    private String name; // 姓名
    private String sex; // 性别
    private String email; // 电子邮件
    private Date birthday; // 出生日期
    private String title; // 职称号
    private String department; // 所属部门 (foreign key)
    private String awardPunishmentId; // 奖惩编号 (foreign key)
    private String studentCode; // 学籍代号 (foreign key)



    // Constructor
    public Teacher(String id, String name, String sex, String email, Date birthday,
                       String title, String department, String awardPunishmentId, String studentCode) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.birthday = birthday;
        this.title = title;
        this.department = department;
        this.awardPunishmentId = awardPunishmentId;
        this.studentCode = studentCode;
    }

    public Teacher() {

    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAwardPunishmentId() {
        return awardPunishmentId;
    }

    public void setAwardPunishmentId(String awardPunishmentId) {
        this.awardPunishmentId = awardPunishmentId;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    // Override toString method for better display
    @Override
    public String toString() {
        return "TeacherInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", title='" + title + '\'' +
                ", department='" + department + '\'' +
                ", awardPunishmentId='" + awardPunishmentId + '\'' +
                ", studentCode='" + studentCode + '\'' +
                '}';
    }
}
