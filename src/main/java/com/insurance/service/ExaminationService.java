package com.insurance.service;

import com.insurance.bean.Examination;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/14.
 */
public interface ExaminationService {

    public List<Examination> findAll();

    public int add(Examination examination);

    public List<Examination> findByIdCard(String idCard);
}
