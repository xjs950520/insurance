package com.insurance.service;

import com.insurance.bean.Examination;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/14.
 */
public interface ExaminationService {

    List<Examination> findAll();

    int add(Examination examination);

    List<Examination> findByIdCard(String idCard);
    List<Examination> findByIdCardAndCheckDate(Examination examination);
}
