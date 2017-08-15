package com.insurance.service.impl;

import com.insurance.bean.Examination;
import com.insurance.dao.ExaminationDao;
import com.insurance.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/14.
 */
@Service
public class ExaminationServiceImpl implements ExaminationService{

    @Autowired
    private ExaminationDao examinationDao;
    @Override
    public List<Examination> findAll() {
        return examinationDao.findAll();
    }

    @Override
    public int add(Examination examination) {
        return examinationDao.add(examination);
    }

    @Override
    public List<Examination> findByIdCard(String idCard) {
        return examinationDao.findByIdCard(idCard);
    }
}
