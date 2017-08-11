package com.insurance.web;

import com.insurance.bean.Register;
import com.insurance.dao.impl.RegisterDaoImpl;
import com.insurance.service.RegisterService;
import com.insurance.service.impl.RegisterServiceImpl;
import com.insurance.util.ExportExcel_Register;
import com.insurance.util.MD5;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
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
        String intro_source=request.getParameter("Intro_source");
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

        int pageSize = Integer.parseInt(request.getParameter("pageSize")==null? "5" :request.getParameter("pageSize"));
        List<Register> bigList = registerService.findAll();
        int l=1;
        for(Register register:bigList){
            register.setNumber(l);
            l++;
        }
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
        request.getSession().setAttribute("list",list);
        /*int i=1;
        for(Register register:list){
            register.setNumber(i);
            i++;
        }*/
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
        return "registerManage";
    }
    @GetMapping(value = "/exportExcel")
    @ResponseBody
    public String registerExcel(HttpServletRequest request, HttpServletResponse response){
        List<Register> list = (List<Register>) request.getSession().getAttribute("list");
        if(list.size()<=0 || list.isEmpty()){
            System.out.println("没有数据");
            return null;
        }
        ExportExcel_Register exportExcel_register = new ExportExcel_Register();
        try {
            HSSFWorkbook wb = exportExcel_register.getRegisterWb(list);

            String dateName = sdf2.format(new Date()).trim();
            String fileName = dateName.substring(0, 4) + dateName.substring(5, 7) + dateName.substring(8, 10) + dateName.substring(11, 13) + dateName.substring(14, 16);

            //自选导出位置
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
            wb.write(response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
