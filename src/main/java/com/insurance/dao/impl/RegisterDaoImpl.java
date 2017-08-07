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
        return 0;
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
        List<Register> list= jdbcTemplate.query("select * from t_register where id = ?", new Object[]{phone},new BeanPropertyRowMapper<>(Register.class));
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }


    @Override
    public List<Register> findAll() {
        return null;
    }
}
