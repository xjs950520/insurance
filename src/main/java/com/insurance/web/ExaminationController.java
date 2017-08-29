package com.insurance.web;

import com.insurance.bean.Examination;
import com.insurance.bean.Register;
import com.insurance.service.ExaminationService;
import com.insurance.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
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
    @Autowired
    private RegisterService registerService;

    @Autowired
    private ResourceLoader resourceLoader;

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
        return "background/examinationManage";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get("/home/backend/image/", filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/import")
    @ResponseBody
    public String examinationImport(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String path= "/home/backend/download";
//        String path=request.getSession().getServletContext().getRealPath("images");//test

        //test
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        File parentFile = new File("/home/backend");
//        File parentFile = new File("D://image");
        String result="";

        String basePath = "/home/backend/";//指定路径，所有图片必须全部放在这里
//        String basePath = "D://image//";//指定路径，所有图片必须全部放在这里
        //test
        if(parentFile.exists()){
            File files[] = parentFile.listFiles();

            Examination examination = null;
            if(files.length != 0){
                for(File file:files){
                    File file1=file;
                    examination = new Examination();
                    String fileName = file.getName();
                    int idCard_position = fileName.indexOf("_");
                    String idCard = fileName.substring(0,idCard_position);
                    examination.setIdCard(idCard);
                    int checkDate_position = fileName.indexOf(".");
                    String checkDate = fileName.substring(idCard_position+1,checkDate_position);
                    String date1 = checkDate.substring(0,4);
                    String date2 = checkDate.substring(4,6);
                    String date3 = checkDate.substring(6,8);
                    String finalCheckDate = date1+"."+date2+"."+date3;
                    examination.setCheck_date(finalCheckDate);
                    List<Examination> slist = examinationService.findByIdCardAndCheckDate(examination);
                    if(slist.size()==0){//代表没有重复数据
                        registerService.updateByIdCard(examination);//代表报名体检的人的数据将要被录入，录入后修改其报名状态
                        String downLoadPath1 = basePath + file.getName();//获得所下载文件的路径
                        //获取输入流
                        bis = new BufferedInputStream(new FileInputStream(downLoadPath1));

                        String upDownPath = path+"//"+idCard; //文件下载后放置目录
                        File upDownFile = new File(upDownPath);
                        if(!upDownFile.exists()){
                            upDownFile.mkdirs();//如果不存在，则创建
                        }
                        //获取输出流
                        bos = new BufferedOutputStream(new FileOutputStream(new File(upDownPath+"//"+fileName)));
                        String url = "/images/"+idCard+"/"+fileName;//数据库放置位置
                        examination.setUrl(url);//数据库存放的url设值
                        examinationService.add(examination);
                        byte[] buff = new byte[2048];
                        int bytesRead;
                        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                            bos.write(buff, 0, bytesRead);
                        }
                        result = "2";
                        //关闭流
                        bis.close();
                        bos.close();
                    }else{//存在已经重复录入过得数据
                        result = "repeat";
                    }
                }

            }else{
                result = "1";//没有要导入的数据
            }
        }else{
            result = "0";//表示路径不符合要求;
        }
        return result;
    }
    @PostMapping(value = "/findExaminationByIdCardAndCheckDate")
    @ResponseBody
    public String findExaminationByIdCardAndCheckDate(String idCard, String check_date, HttpServletRequest request){
        Examination examination = new Examination();
        examination.setCheck_date(check_date);
        examination.setIdCard(idCard);
        List<Examination> list = examinationService.findByIdCardAndCheckDate(examination);
        request.getSession().setAttribute("examinations",list);
        String result="";
        if(list.size()>0){
          result="true";
        }else{
           result="false";
        }
        return  result;
    }
    @GetMapping(value = "/lookDetails")
    public String lookDetail(HttpServletRequest request){
        request.setAttribute("examinations",request.getSession().getAttribute("examinations"));
        if(request.getParameter("mark").equals("1")){
            return "background/showExaminationDetail";
        }else if(request.getParameter("mark").equals("2")){
            return "front/showExaminationDetail";
        }

       return  null;
    }
    /*@GetMapping(value = "/findExaminationByIdCard")
    public String findExaminationByIdCard(String idCard, HttpServletRequest request){
        List<Examination> list = examinationService.findByIdCard(idCard);
        int size = list.size();
        request.setAttribute("examinations",list);
        return "background/showExaminationDetail";
    }*/
    /*@GetMapping(value = "/lookForExamination")
    public String lookForExamination(HttpServletRequest request){
        String phone = (String) request.getSession().getAttribute("phone");
        if(phone != null && !phone.equals("")){//已登录
            Register register = registerService.getRegisterByPhone(phone);
            if(register.getIdCard()==null || register.getIdCard().equals("")){//已登录但未绑定身份证号
                return "front/addIdCard";
            }else{//已绑定身份证号
                String idCard=register.getIdCard();
                List<Examination> list = examinationService.findByIdCard(idCard);
                int size = list.size();
                request.setAttribute("examinations",list);
                return "background/showExaminationDetail";
            }
        }else{//未登录
            request.getSession().setAttribute("mark",2);
            return "front/login";
        }
    }*/
    @GetMapping(value = "/lookForExamination")
    public String showExaminationList(HttpServletRequest request){
        String phone = (String) request.getSession().getAttribute("phone");
        if(phone != null && !phone.equals("")){//已登录
            Register register = registerService.getRegisterByPhone(phone);
            if(register.getIdCard()==null || register.getIdCard().equals("")){//已登录但未绑定身份证号
                return "front/addIdCard";
            }else{//已绑定身份证号
                String idCard=register.getIdCard();
                String name=registerService.findByIdCard(idCard).getName();
                List<Examination> list = examinationService.findByIdCard(idCard);
                for(Examination examination:list){
                    examination.setName(name);
                }
                int size = list.size();
                request.setAttribute("examinations",list);
                return "front/showExaminationList";
            }
        }else{//未登录
            request.getSession().setAttribute("mark",2);
            return "front/login";
        }
    }

}
