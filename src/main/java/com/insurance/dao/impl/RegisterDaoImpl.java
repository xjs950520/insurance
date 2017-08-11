package com.insurance.dao.impl;

import com.insurance.bean.Register;
import com.insurance.dao.RegisterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/7.
 */
@Repository
public class RegisterDaoImpl implements RegisterDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(Register register) {
        int resRow = jdbcTemplate.update("insert into register(name, phone, password, intro_phone, intro_source,ct_date) VALUES(?,?,?,?,?,?) ", new Object[]{
            register.getName(), register.getPhone(), register.getPassword(), register.getIntro_phone(), register.getIntro_source(),register.getCt_date()
        });
        return resRow;
    }

    @Override
    public int del(Register register) {
        return 0;
    }

    @Override
    public int update(Register register) {
        return 0;
    }

    @Override
    public Register getRegisterByPhone(String phone) {
        List<Register> list= jdbcTemplate.query("select * from register where phone = ?", new Object[]{phone},new BeanPropertyRowMapper<>(Register.class));
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }


    @Override
    public List<Register> findAll() {
        List<Register> list = jdbcTemplate.query("select * from register r LEFT JOIN introducer i on r.intro_phone=i.intro_phone ORDER by r.ct_date DESC", new Object[]{}, new BeanPropertyRowMapper(Register.class));
        if(list != null){
            return list;
        }else{

            return list;
        }
    }
}
