package com.insurance.service.impl;

import com.insurance.bean.Register;
import com.insurance.dao.JoinDao;
import com.insurance.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/11.
 */
@Service
public class JoinServiceImpl implements JoinService {

    @Autowired
    private JoinDao joinDao;

    @Override
    public List<Register> findAll() {
        return joinDao.findAll();
    }

    @Override
    public int update(Register register) {
        return joinDao.update(register);
    }
}
