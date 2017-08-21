package com.insurance.service.impl;

import com.insurance.bean.Examination;
import com.insurance.bean.Register;
import com.insurance.dao.RegisterDao;
import com.insurance.dao.impl.RegisterDaoImpl;
import com.insurance.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/7.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterDao registerDao;

    @Override
    public int add(Register register) {
        return registerDao.add(register);
    }

    @Override
    public int del(Register register) {
        return 0;
    }

    @Override
    public int updateByPhone(Register register) {

        return registerDao.updateByPhone(register);
    }

    @Override
    public Register getRegisterByPhone(String phone) {
        return registerDao.getRegisterByPhone(phone);
    }

    @Override
    public int updateByIdCard(Examination examination) {
        return registerDao.updateByIdCard(examination);
    }

    @Override
    public Register findByIdCard(String idCard) {
        return registerDao.findByIdCard(idCard);
    }

    @Override
    public List<Register> findAll() {
        return registerDao.findAll();
    }

    @Override
    public Register getRegisterByName(String name) {
        return registerDao.getRegisterByName(name);
    }

    @Override
    public int addIdCard(Register register) {
        return registerDao.addIdCard(register);
    }

}
