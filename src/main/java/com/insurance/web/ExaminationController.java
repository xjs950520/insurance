package com.insurance.web;

import com.insurance.bean.Examination;
import com.insurance.bean.Register;
import com.insurance.service.ExaminationService;
import com.insurance.service.RegisterService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceClient;
import java.awt.image.BufferedImage;
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

    @RequestMapping(method = RequestMethod.GET, /*value = "/{filename:.+}",*/value="/downPdf",produces = "application/pdf")
    @ResponseBody
    public ResponseEntity<?> getFile(/*@PathVariable String filename,*/HttpServletRequest request) {
        String filename=request.getParameter("pdf");
        /*if(filename.indexOf("pdf")!=-1){
            try {
//            filename = filename+".jpg";
//           filename="412728199505263257_20170829.jpg";
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get("/home/backend/image/", filename).toString()));
//                return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get("D:/image/", filename).toString()));
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }else{
            filename=filename.substring(0,filename.indexOf("."));
            try {
//                InputStream is = new FileInputStream("D:/image/" + filename+".pdf");
                InputStream is = new FileInputStream("/home/backend/image/" + filename+".pdf");
                PDDocument pdf = PDDocument.load(is);
                int actSize = pdf.getNumberOfPages();
                List<BufferedImage> piclist = new ArrayList<BufferedImage>();
                for (int i = 0; i < actSize; i++) {
                    BufferedImage image = new PDFRenderer(pdf).renderImageWithDPI(i, 130, ImageType.RGB);
                    piclist.add(image);
                }

//                yPic(piclist, "D:/imgs/" + filename + ".jpg");
                yPic(piclist, "/home/backend/image/" + filename + ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/


        try {
//            filename = filename+".jpg";
//           filename="412728199505263257_20170829.jpg";

            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get("/home/backend/image/", filename).toString()));
//           return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get("D:/imgs/", filename+".jpg").toString()));
//            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get("D:/image/", filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    public static void yPic(List<BufferedImage> piclist,String outPath){
        if(piclist == null || piclist.size()<=0){
            System.out.println("图片数组为空！");
            return;
        }
        try{
            int height=0,//总高度
            width=0,//总宽度
            _height=0,//临时高度，或保存偏移高度
            __height=0,//临时高度，主要保存每个高度
            picNum = piclist.size();//图片数量
            int[] heightArray = new int[picNum];
            BufferedImage buffer = null;//保存图片流
            List<int[]> imgRGB = new ArrayList<int[]>();//保存所有图片的RGB
            int[] _imgRGB;//保存一张图片的RGB数据
            for(int i = 0;i<picNum;i++){
                buffer = piclist.get(i);
                heightArray[i] = _height = buffer.getHeight();//图片高度
                if(i==0){
                    width=buffer.getWidth();
                }
                height += _height;//获取总高度
                _imgRGB = new int[width * _height];//从图片中读取RGB
                _imgRGB = buffer.getRGB(0,0,width,_height,_imgRGB,0,width);
                imgRGB.add(_imgRGB);
            }
            _height = 0;//设置偏移高度为0
            //生成新图片
            BufferedImage imageResult = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            for(int i=0;i<picNum;i++){
               int le = heightArray.length;
                __height = heightArray[i];
                if(i !=0) _height += __height;//计算偏移高度
                int l = imgRGB.size();
                imageResult.setRGB(0,_height,width,__height,imgRGB.get(i),0,width);//写入输入流
            }
            File outFile = new File(outPath);
            ImageIO.write(imageResult,"jpg",outFile);
        }catch (Exception e){
            e.printStackTrace();
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
        String paramas = request.getParameter("paramas");
        Examination examination = new Examination();
        examination.setCheck_date(check_date);
        examination.setIdCard(idCard);
        List<Examination> list = examinationService.findByIdCardAndCheckDate(examination);
        request.getSession().setAttribute("examinations",list);
        String result="";
        if(list.size()>0){
          /*result="true";*/
          String fileName = list.get(0).getUrl();
//          if(paramas!=null){
//              result=fileName.substring(0,fileName.indexOf("."))+".pdf";
            result = fileName;
//          }else{
//              result=fileName.substring(0,fileName.indexOf("."))+".jpg";
//          }

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
