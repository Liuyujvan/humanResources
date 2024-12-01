package com.nowcoder.humanresources.entity;
/**
 *
 * @author WCX
 * 学籍经历
 */
public class School {
    private String id; // 工号
    private String studentCode; // 学籍代号, primary key
    private String name; // 姓名
    private String primarySchool; // 小学学历
    private String juniorHigh; // 初中学历
    private String seniorHigh; // 高中学历
    private String undergraduate; // 本科学历
    private String graduate; // 研究生学历
    private String doctorate; // 博士学历

    // Constructor
    public School(String id, String studentCode, String name, String primarySchool, String juniorHigh,
                          String seniorHigh, String undergraduate, String graduate, String doctorate) {
        this.id = id;
        this.studentCode = studentCode;
        this.name = name;
        this.primarySchool = primarySchool;
        this.juniorHigh = juniorHigh;
        this.seniorHigh = seniorHigh;
        this.undergraduate = undergraduate;
        this.graduate = graduate;
        this.doctorate = doctorate;
    }

    /**
     * Constructs a new object.
     */
    public School() {
    }
// Getters and Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimarySchool() {
        return primarySchool;
    }

    public void setPrimarySchool(String primarySchool) {
        this.primarySchool = primarySchool;
    }

    public String getJuniorHigh() {
        return juniorHigh;
    }

    public void setJuniorHigh(String juniorHigh) {
        this.juniorHigh = juniorHigh;
    }

    public String getSeniorHigh() {
        return seniorHigh;
    }

    public void setSeniorHigh(String seniorHigh) {
        this.seniorHigh = seniorHigh;
    }

    public String getUndergraduate() {
        return undergraduate;
    }

    public void setUndergraduate(String undergraduate) {
        this.undergraduate = undergraduate;
    }

    public String getGraduate() {
        return graduate;
    }

    public void setGraduate(String graduate) {
        this.graduate = graduate;
    }

    public String getDoctorate() {
        return doctorate;
    }

    public void setDoctorate(String doctorate) {
        this.doctorate = doctorate;
    }
}