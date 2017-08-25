package com.insurance.dao.impl;

import com.insurance.bean.Examination;
import com.insurance.bean.Introducer;
import com.insurance.dao.ExaminationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/14.
 */

@Repository
public class ExaminationDaoImpl implements ExaminationDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Examination> findAll() {
        List<Examination> list = jdbcTemplate.query("select * from examination order by check_date desc", new Object[]{}, new BeanPropertyRowMapper(Examination.class));
        return list;
    }

    @Override
    public int add(Examination examination) {
        int resRow = jdbcTemplate.update("insert into examination(idCard, check_date, url) VALUES(?,?,?) ", new Object[]{
                examination.getIdCard(),examination.getCheck_date(),examination.getUrl()
        });
        return resRow;
    }

    @Override
    public List<Examination> findByIdCardAndCheckDate(Examination examination) {
        List<Examination> list = jdbcTemplate.query("select * from examination e where e.idCard=? and e.check_date=? order by e.check_date DESC " , new Object[]{examination.getIdCard(), examination.getCheck_date()}, new BeanPropertyRowMapper(Examination.class));
        return list;
    }

    @Override
    public List<Examination> findByIdCard(String idCard) {
        List<Examination> list = jdbcTemplate.query("select * from examination e where e.idCard=? order by check_date desc", new Object[]{idCard}, new BeanPropertyRowMapper(Examination.class));
        return list;
    }
}
