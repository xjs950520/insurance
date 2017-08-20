package com.insurance.service;

import com.insurance.bean.Register;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/7.
 */
public interface RegisterService {
    int add(Register register);

    int del(Register register);

    int updateByPhone(Register register);

    Register getRegisterByPhone(String phone);
    Register findByIdCard(String idCard);//通过身份证号查询
    List<Register> findAll();
    Register getRegisterByName(String name);
    int addIdCard(Register register);//添加身份证号
}
