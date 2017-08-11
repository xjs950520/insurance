package com.insurance.web;

import com.insurance.bean.Introducer;
import com.insurance.service.IntroducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xujunshuai on 2017/8/11.
 */
@Controller
@RequestMapping(value = "/introducerController")
public class IntroducerController {

    @Autowired
    private IntroducerService introducerService;

    @RequestMapping(value = "/findAll")
    public String findAll(HttpServletRequest request){

        int pageno = Integer.parseInt(request.getParameter("pageno")==null || request.getParameter("pageno").equals("")?"1":request.getParameter("pageno"));

        int pageSize = Integer.parseInt(request.getParameter("pageSize")==null? "5" :request.getParameter("pageSize"));
        List<Introducer> bigList = introducerService.findAll();

        int l=1;
        for(Introducer introducer:bigList){
            introducer.setNumber(l);
            l++;
        }
        List<Introducer> list = new ArrayList<Introducer>();
        int count=bigList.size();//总条数
        request.setAttribute("count",count);
        //计算开始标识 = 页数大小 * （页码-1）
        int beginIndex = pageSize*(pageno - 1);
        //结束标识
        int endIndex = pageSize*pageno>bigList.size()?bigList.size():pageSize*pageno;
        for(int i=beginIndex;i<endIndex;i++){
            list.add(bigList.get(i));
        }
        int totalPage=0;
        double j = (double)count/pageSize;//计算共有多少页
        if(Math.round(j)!=j ){
            totalPage=(int)j+1;
        }else{
            totalPage = (int) j;
        }
        
        int currentPage=pageno;
        request.setAttribute("totalPage",totalPage);
        request.setAttribute("registers",list);
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("pageSize",pageSize);
        return "introducerManage";
    }
}
