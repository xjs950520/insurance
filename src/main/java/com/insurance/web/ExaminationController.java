package com.insurance.web;

import com.insurance.bean.Examination;
import com.insurance.bean.Register;
import com.insurance.service.ExaminationService;
import com.insurance.service.RegisterService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceClient;
import java.io.*;
import java.net.URLEncoder;
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
            /*return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get("D:/image/", filename).toString()));*/
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/import")
    @ResponseBody
    public String examinationImport(@RequestParam("file") MultipartFile file) throws Exception{
        String fileName = file.getOriginalFilename();

        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String path = "/home/backend/image/";
        /*String path="D:/image/";*/
        File dest = new File(path+fileName);

        Examination examination = new Examination();
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
        if(slist.size()==0){
            registerService.updateByIdCard(examination);
            if(!dest.getParentFile().exists()){
                dest.mkdirs();
            }
            file.transferTo(dest);
            examination.setUrl(fileName);
            examinationService.add(examination);
        }else{
            return "2";
        }
        return "1";
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
          /*result="true";*/
          result=list.get(0).getUrl();
        }else{
           result="false";
        }
        return  result;
    }
    @PostMapping(value="/toDownFile")
    @ResponseBody
    public String toDownFile(String idCard, String check_date, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("idCard",idCard);
        session.setAttribute("check_date",check_date);
        return "true";
    }
    @GetMapping(value = "/downFile")
    @ResponseBody
    public String downFile(HttpServletRequest request,HttpServletResponse response){

        Examination examination = new Examination();
        HttpSession session = request.getSession();
        if(session!=null){
            String idCard= (String) session.getAttribute("idCard");
            String check_date = (String) session.getAttribute("check_date");
            if(idCard!=null && check_date!=null){
                examination.setCheck_date(check_date);
                examination.setIdCard(idCard);
                List<Examination> list = examinationService.findByIdCardAndCheckDate(examination);
                request.getSession().setAttribute("examinations",list);
                if(list.size()>0){
                    String fileName=list.get(0).getUrl();
                    FileInputStream in=null;
                    OutputStream out=null;
                    try{
                        fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
                        //本地测试
//                        String path="D:/";
                        String path="/home/backend/image/";
                        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                        in = new FileInputStream(path + "/" + fileName);
                        out = response.getOutputStream();
                        byte buffer[] = new byte[1024];
                        int len = 0;
                        while((len=in.read(buffer))>0){
                            out.write(buffer, 0, len);
                        }

                        /*HttpHeaders headers = new HttpHeaders();
                        File file = new File(path+"/"+fileName);
                        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                        headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("utf-8"), "ISO8859-1"));
                        return new ResponseEntity<byte[]>(FileUtils.,
                                headers, HttpStatus.CREATED);*/
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if(in!=null){
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if(out!=null){
                            try {
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }

        }


        return  null;
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
    @RequestMapping(value="/many")
    public String many(){
        return "front/many";
    }
}
