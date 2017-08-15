package com.insurance.service;

import com.insurance.bean.Register;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/11.
 */
public interface JoinService {
    public List<Register> findAll();//参加体验活动人查询
    public int update(Register register);
}
