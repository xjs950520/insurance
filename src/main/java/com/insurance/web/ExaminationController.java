package com.insurance.web;

import com.insurance.bean.Examination;
import com.insurance.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xujunshuai on 2017/8/14.
 */
@Controller
@RequestMapping(value = "/examinationController")
public class ExaminationController {

    @Autowired
    private ExaminationService examinationService;

    private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");

    @GetMapping(value = "/findAll")
    public String findAll(HttpServletRequest request){
        int pageno = Integer.parseInt(request.getParameter("pageno")==null || request.getParameter("pageno").equals("")?"1":request.getParameter("pageno"));
        int pageSize=5;
        /*if(request.getSession().getAttribute("p")!=null){
            String pa = (String) request.getAttribute("p");
        }*/
        if(request.getParameter("pageSize")!=null){
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }else if(request.getSession().getAttribute("p")!=null && request.getParameter("pageSize")==null){
            pageSize = (int) request.getSession().getAttribute("p");
        }
        /*int pageSize = Integer.parseInt(request.getParameter("pageSize")==null? "5" :request.getParameter("pageSize"));*/
        List<Examination> bigList = examinationService.findAll();
        int l=1;
        for(Examination examination:bigList){
            examination.setNumber(l);
            l++;
        }
        List<Examination> list = new ArrayList<Examination>();
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
        request.setAttribute("examinations",list);
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("pageSize",pageSize);
        request.getSession().setAttribute("p", pageSize);
        return "examinationManage";
    }
    @GetMapping(value = "/import")
    @ResponseBody
    public String examinationImport(){
        File parentFile = new File("D://image");
        String result="";
        if(parentFile.exists()){
            File files[]=parentFile.listFiles();
            Examination examination=null;
            if(files.length!=0){
                for(File file:files){
                    examination = new Examination();
                    String fileName = file.getName();
                    int idCard_position = fileName.indexOf("_");
                    String idCard = fileName.substring(0,idCard_position);
                    examination.setIdCard(idCard);
                    int checkDate_position = fileName.indexOf(".");
                    String checkDate = fileName.substring(idCard_position+1,checkDate_position);
                    examination.setCheck_date(checkDate);
                    examinationService.add(examination);
                }
                result = "2";//导入成功
            }else{
                result = "1";//没有要导入的数据
            }

        }else{
            result = "0";//表示路径不符合要求
        }
        return result;
    }
}
