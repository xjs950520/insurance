package com.insurance.bean;

/**
 * Created by xujunshuai on 2017/8/7.
 */
public class Register {
    private String name;
    private String phone;
    private String password;
    private String card;
    private String introducer;
    private int intro_source;
    private String ct_date;

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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
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
}
