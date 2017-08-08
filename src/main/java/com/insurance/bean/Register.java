package com.insurance.bean;

/**
 * 注册人员
 * Created by xujunshuai on 2017/8/7.
 */
public class Register {
    private String name; //姓名
    private String phone; //手机号
    private String password; //密码
    private String idCard; //身份证
    private int introducer; //推荐人
    private int intro_source; //注册来源，关联intro_source
    private String ct_date; //注册时间
    private int status; //是否报名体检，0是未，

    public Register() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getIntroducer() {
        return introducer;
    }

    public void setIntroducer(int introducer) {
        this.introducer = introducer;
    }

    public int getIntro_source() {
        return intro_source;
    }

    public void setIntro_source(int intro_source) {
        this.intro_source = intro_source;
    }

    public String getCt_date() {
        return ct_date;
    }

    public void setCt_date(String ct_date) {
        this.ct_date = ct_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

