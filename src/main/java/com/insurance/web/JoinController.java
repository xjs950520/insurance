package com.insurance.web;

import com.insurance.bean.Register;
import com.insurance.service.JoinService;
import com.insurance.service.RegisterService;
import com.insurance.util.ExportExcel_Register;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xujunshuai on 2017/8/11.
 */
@Controller
@RequestMapping(value = "/joinController")
public class JoinController {

    @Autowired
    private JoinService joinService;

    @Autowired
    private RegisterService registerService;

    private final SimpleDateFormat sdf2=new SimpleDateFormat("yyyy.MM.dd HH:mm");

    //跳转页面
    @GetMapping(value = "/toJoin")
    public String toJoin(HttpServletRequest request){
        String phone = (String) request.getSession().getAttribute("phone");
        if(phone==null || phone.equals("")){
            request.getSession().setAttribute("mark",1);
            return "front/login";
        }else if(phone!=null && !phone.equals("")){
            Register register = registerService.getRegisterByPhone(phone);
            String name= register.getName();
            request.setAttribute("name",name);
            request.setAttribute("phone",phone);
            return "front/join";
        }
        return null;
    }

    @PostMapping(value = "/join")
    @ResponseBody
    public String join(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String experience_card = request.getParameter("experience_card");
        String meal_sort = null;
        if(Integer.parseInt(experience_card)>200){
            meal_sort = "其他套餐";
        }else if(200>=Integer.parseInt(experience_card) && Integer.parseInt(experience_card)>=1){
            meal_sort = "vip套餐";
        }
        String result = "false";
        Register register = registerService.getRegisterByPhone(phone);
        if(register.getJoin_status()==1){
            result = "exist";//表示已报过名
        }else{
            register = new Register();
            register.setPhone(phone);
            register.setExperience_card(experience_card);
            register.setMeal_sort(meal_sort);
            register.setJoin_status(1);
            register.setJoin_date(sdf2.format(new Date()));
            int count = joinService.update(register);
            if(count>0){
                result = "true";
            }
        }
        return result;
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

        int pageSize = 5;
        if(request.getParameter("pageSize")!=null){
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }else if(request.getSession().getAttribute("p")!=null && request.getParameter("pageSize")==null){
            pageSize = (int) request.getSession().getAttribute("p");
        }
        List<Register> bigList = joinService.findAll();
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

        int totalPage=0;
        double j = (double)count/pageSize;//计算共有多少页
        if(Math.round(j)!=j ){
            totalPage=(int)j+1;
        }else{
            totalPage = (int) j;
        }

        int currentPage = pageno;
        request.setAttribute("totalPage",totalPage);
        request.setAttribute("registers",list);
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("pageSize",pageSize);
        request.getSession().setAttribute("p", pageSize);
        return "background/joinManage";
    }
    @GetMapping(value = "/exportExcel")
    @ResponseBody
    public String registerExcel(HttpServletRequest request, HttpServletResponse response){
        List<Register> list = (List<Register>) request.getSession().getAttribute("list");
        if(list.size()<=0 || list.isEmpty()){
            return null;
        }
        ExportExcel_Register exportExcel_register = new ExportExcel_Register();
        try {
            HSSFWorkbook wb = exportExcel_register.getJoinRegisterWb(list);
            File file=new File("");


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
