package com.insurance.dao;

import com.insurance.bean.Introducer;
import com.insurance.bean.Register;
import java.util.List;

/**
 * Created by xujunshuai on 2017/8/11.
 */
public interface IntroducerDao {

    public List<Introducer> findAll();

    public List<Introducer> findByPhone(Introducer introducer);

    public int add(Introducer introducer);
}
