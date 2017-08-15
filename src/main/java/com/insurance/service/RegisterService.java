package com.insurance.service;

import com.insurance.bean.Register;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/7.
 */
public interface RegisterService {
    int add(Register register);

    int del(Register register);

    int update(Register register);

    Register getRegisterByPhone(String phone);

    List<Register> findAll();
    Register getRegisterByName(String name);
}
