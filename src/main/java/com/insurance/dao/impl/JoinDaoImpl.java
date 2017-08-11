package com.insurance.dao.impl;

import com.insurance.bean.Register;
import com.insurance.dao.JoinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/11.
 */
@Repository
public class JoinDaoImpl implements JoinDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Register> findAll() {
        List<Register> list = jdbcTemplate.query("select * from register r LEFT JOIN introducer i ON r.intro_phone=i.intro_phone where r.join_status=1 ORDER BY r.join_date desc", new Object[]{}, new BeanPropertyRowMapper(Register.class));
        if(list != null){
            return list;
        }else{

            return list;
        }
    }
}
