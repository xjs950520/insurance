package com.insurance.bean;

/**推荐人员
 * Created by xushuaijun on 2017-08-09 .
 */
public class Introducer {

    private int id;
    private int number;//假字段
    private String intro_name; //推荐人姓名
    private String intro_phone; //手机号

    public Introducer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntro_name() {
        return intro_name;
    }

    public void setIntro_name(String intro_name) {
        this.intro_name = intro_name;
    }

    public String getIntro_phone() {
        return intro_phone;
    }

    public void setIntro_phone(String intro_phone) {
        this.intro_phone = intro_phone;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
