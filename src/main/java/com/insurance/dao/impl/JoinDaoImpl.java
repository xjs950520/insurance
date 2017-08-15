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

    @Override
    public int update(Register register) {
        int num = jdbcTemplate.update("update register r SET r.experience_card = ?, r.meal_sort = ?, r.join_status = ?, r.join_date = ? where r.phone = ?",
                register.getExperience_card(),register.getMeal_sort(),register.getJoin_status(),register.getJoin_date(),register.getPhone());
        return num;
    }
}
