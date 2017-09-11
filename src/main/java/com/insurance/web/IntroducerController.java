package com.insurance.web;

import com.insurance.bean.Introducer;
import com.insurance.service.IntroducerService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
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

    private ResourceLoader resourceLoader;

    @RequestMapping(value = "/findAll")
    public String findAll(HttpServletRequest request){

        int pageno = Integer.parseInt(request.getParameter("pageno")==null || request.getParameter("pageno").equals("")?"1":request.getParameter("pageno"));
        int pageSize = 5;
        if(request.getParameter("pageSize")!=null){
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }else if(request.getSession().getAttribute("p")!=null && request.getParameter("pageSize")==null){
            pageSize = (int) request.getSession().getAttribute("p");
        }
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
        request.getSession().setAttribute("p", pageSize);
        return "background/introducerManage";
    }
   /* //进入介绍人导入界面
    @RequestMapping("/toExcelImport")
    public String toExcelImport(){
        return "chooseExcel";
    }*/

    @PostMapping(value="/excelImport")
    @ResponseBody
    public String excelImport(@RequestParam("file") MultipartFile file) throws IOException {
		//获取文件名
        String fileName = file.getOriginalFilename();

        //文件上传后的路径-->本地
        /*String path = "D://IdeaProjects//insurance//src//main//resources//static//";*/
        //文件上传后的路径-->服务器
        String path = "/home/backend/image/";
        File dest = new File(path+fileName);
        /*File dest = new File(path+fileName);*/
        if(!dest.getParentFile().exists()){
            dest.mkdirs();
        }
        file.transferTo(dest);

        String putFileName=path+"/"+fileName;//获得上传文件的路径
        File existFile=new File(putFileName);
        String totalRs="1";//1:录入成功 2:失败
        if(existFile.exists()){
            InputStream fileIn=new BufferedInputStream(new FileInputStream(new File(putFileName)));
            Workbook wb0=null;//根据制定的文件输入流导入Excel从而产生Workbook对象
            if(existFile.getName().endsWith("xls")){
                wb0=new HSSFWorkbook(fileIn);
            }else if(existFile.getName().endsWith("xlsx")){
                wb0=new XSSFWorkbook(fileIn);
            }
            Sheet sht0=wb0.getSheetAt(0);//获取Excel文档中第一个表单

            Introducer introducer = null;
            int rows = 0;
            for(Row r:sht0){
                //根据Excel表格模板从第2行才是正式数据
                if(r.getRowNum() < 1){
                    continue;
                }
                if(r.getCell(1) != null && !r.getCell(1).toString().equals("")){
                    introducer = new Introducer();
                    String name = r.getCell(1).toString();
                    introducer.setIntro_name(r.getCell(1).toString());
                }
                if(r.getCell(2) != null && !r.getCell(2).toString().trim().equals("")){
                    BigDecimal bd = new BigDecimal(r.getCell(2).toString());
                    String phone = bd.toPlainString();
                    introducer.setIntro_phone(phone);
                }
                if(introducerService.findByPhone(introducer).size()>0){
                    continue;
                }
                rows = introducerService.add(introducer);
            }
            if(rows < 0){
                totalRs = "2";
            }
            existFile.delete();
            if(fileIn != null){
                fileIn.close();
            }

        }
        return totalRs;
        /*try {
            response.getWriter().println(totalRs);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }
}
