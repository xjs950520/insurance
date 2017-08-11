package com.insurance.service.impl;

import com.insurance.bean.Introducer;
import com.insurance.dao.IntroducerDao;
import com.insurance.service.IntroducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/11.
 */
@Service
public class IntroducerServiceImpl implements IntroducerService {

    @Autowired
    private IntroducerDao introducerDao;
    @Override
    public List<Introducer> findAll() {
        return introducerDao.findAll();
    }
}
