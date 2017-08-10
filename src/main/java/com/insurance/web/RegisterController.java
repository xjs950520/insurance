package com.insurance.web;

import com.insurance.bean.Register;
import com.insurance.dao.impl.RegisterDaoImpl;
import com.insurance.service.RegisterService;
import com.insurance.service.impl.RegisterServiceImpl;
import com.insurance.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xujunshuai on 2017/8/9.
 */
@Controller
@RequestMapping(value = "/registerController")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");

    private final SimpleDateFormat sdf2=new SimpleDateFormat("yyyy.MM.dd HH:mm");

    @PostMapping(value = "/add")
    @ResponseBody
    public int add(HttpServletRequest request, HttpServletResponse response){
        String name=request.getParameter("name");
        String phone=request.getParameter("phone");
        String password=request.getParameter("password");
        String intro_phone=request.getParameter("intro_phone");
        String intro_source=request.getParameter("intro_source");
        String ct_date=sdf.format(new Date());
        Register register = new Register();
        register.setName(name);
        register.setPhone(phone);
        register.setPassword(MD5.md5(password));
        register.setIntro_phone(intro_phone);
        register.setIntro_source(intro_source);
        register.setCt_date(ct_date);
        int i=registerService.add(register);
        return  i;

    }
    @GetMapping(value = "/findAll")
    public String findAll(HttpServletRequest request){
        /*List<Register> list=registerService.findAll();
        int i=1;
        for(Register register:list){
            register.setNumber(i);
            i++;
        }
        request.setAttribute("registers",list);
        return "registerManage";*/
        int pageno = Integer.parseInt(request.getParameter("pageno")==null || request.getParameter("pageno").equals("")?"1":request.getParameter("pageno"));

        int pageSize = Integer.parseInt(request.getParameter("pageSize")==null? "1" :request.getParameter("pageSize"));
        List<Register> bigList = registerService.findAll();
        List<Register> list = new ArrayList<Register>();
        int count=bigList.size();//总条数
        request.setAttribute("count",count);
        //计算开始标识 = 页数大小 * （页码-1）
        int beginIndex = pageSize*(pageno - 1);
        //结束标识
        int endIndex = pageSize*pageno>bigList.size()?bigList.size():pageSize*pageno;
        for(int i=beginIndex;i<endIndex;i++){
            list.add(bigList.get(i));
        }
        int i=1;
        for(Register register:list){
            register.setNumber(i);
            i++;
        }
        int totalPage=0;
       double j = (double)count/pageSize;
        if(Math.round(j)!=j){
            totalPage=(int)j+1;
        }else{
            System.out.println("是一个整数");
        }
        totalPage =(count/1)+1; //共多少页

        int currentPage=pageno;
        request.setAttribute("totalPage",totalPage);
        request.setAttribute("registers",list);
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("pageSize",pageSize);
        return "registerManage";
    }
}
