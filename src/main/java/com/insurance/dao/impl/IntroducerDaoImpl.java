package com.insurance.dao.impl;

import com.insurance.bean.Introducer;
import com.insurance.dao.IntroducerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/11.
 */
@Repository
public class IntroducerDaoImpl implements IntroducerDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Introducer> findAll() {
        List<Introducer> list = jdbcTemplate.query("select * from introducer", new Object[]{}, new BeanPropertyRowMapper(Introducer.class));
        if(list != null){
            return list;
        }else{

            return list;
        }
    }
}
