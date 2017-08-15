package com.insurance.dao;

import com.insurance.bean.Examination;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/14.
 */
public interface ExaminationDao {

    public List<Examination> findAll();//查询所有报告

    public int add(Examination examination);//添加

    public List<Examination> findByIdCard(String idCard);//按身份证查询报告
}
