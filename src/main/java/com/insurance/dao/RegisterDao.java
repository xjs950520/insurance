package com.insurance.dao;

import com.insurance.bean.Register;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/7.
 */
public interface RegisterDao {

    public int add(Register register);

    public int del(Register register);

    public int update(Register register);

    public Register getRegisterByPhone(String phone);

    public List<Register> findAll();

}
