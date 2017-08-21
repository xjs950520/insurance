package com.insurance.dao;

import com.insurance.bean.Examination;
import com.insurance.bean.Register;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/7.
 */
public interface RegisterDao {

    int add(Register register);//注册

    int del(Register register);//删除

    int updateByPhone(Register register);//修改

    int updateByIdCard(Examination examination);//通过身份证号修改

    int addIdCard(Register register);//添加身份证号

    Register getRegisterByPhone(String phone);//通过手机号查找

    List<Register> findAll();//注册人查询、

    Register findByIdCard(String idCard);//通过身份证号查询

    Register getRegisterByName(String name);//通过注册名查找

}
