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
    private String intro_phone; //推荐手机号
    private String intro_source; //注册来源，关联intro_source
    private String ct_date; //注册时间
    private int join_status; //是否报名体检，0是未，
    private String meal_sort;//套餐种类
    private String experience_card;//体验卡卡号
    private String intro_name;//假字段，推荐人姓名
    private int number;//假字段
    private String join_date;//报名时间

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

    public String getIntro_phone() {
        return intro_phone;
    }

    public void setIntro_phone(String intro_phone) {
        this.intro_phone = intro_phone;
    }

    public String getIntro_source() {
        return intro_source;
    }

    public void setIntro_source(String intro_source) {
        this.intro_source = intro_source;
    }

    public String getCt_date() {
        return ct_date;
    }

    public void setCt_date(String ct_date) {
        this.ct_date = ct_date;
    }

    public int getJoin_status() {
        return join_status;
    }

    public void setJoin_status(int join_status) {
        this.join_status = join_status;
    }

    public String getIntro_name() {
        return intro_name;
    }

    public void setIntro_name(String intro_name) {
        this.intro_name = intro_name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMeal_sort() {
        return meal_sort;
    }

    public void setMeal_sort(String meal_sort) {
        this.meal_sort = meal_sort;
    }

    public String getExperience_card() {
        return experience_card;
    }

    public void setExperience_card(String experience_card) {
        this.experience_card = experience_card;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }
}

