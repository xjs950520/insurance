package com.insurance.dao;

import com.insurance.bean.Examination;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/14.
 */
public interface ExaminationDao {

    public List<Examination> findAll();

    public int add(Examination examination);
}
