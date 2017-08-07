package com.insurance.service.impl;

import com.insurance.bean.Register;
import com.insurance.dao.RegisterDao;
import com.insurance.dao.impl.RegisterDaoImpl;
import com.insurance.service.RegisterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/7.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    private RegisterDao registerDao;
    @Override
    public int add(Register register) {
        return 0;
    }

    @Override
    public int del(Register register) {
        return 0;
    }

    @Override
    public int update(Register register) {
        return 0;
    }

    @Override
    public Register getRegisterByPhone(String phone) {
        return registerDao.getRegisterByPhone(phone);
    }

    @Override
    public List<Register> findAll() {
        return null;
    }
}
