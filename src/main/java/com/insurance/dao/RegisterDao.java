package com.insurance.dao;

import com.insurance.bean.Register;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/7.
 */
public interface RegisterDao {

    int add(Register register);//注册

    int del(Register register);//删除

    int update(Register register);//修改

    Register getRegisterByPhone(String phone);//通过手机号查找

    List<Register> findAll();//注册人查询、

    Register getRegisterByName(String name);//通过注册名查找

}
