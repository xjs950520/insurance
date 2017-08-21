package com.insurance.util;

import com.insurance.bean.Register;
import org.apache.poi.hssf.usermodel.*;

import java.util.List;

/**
 * Created by xujunshuai on 2017/8/11.
 */
public class ExportExcel_Register {
    public HSSFWorkbook getRegisterWb(List<Register> registerExcelList){
        //第一步，创建一个webbook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //第二步，在webbook中添加一个sheet，对应Excel文件中的sheet,生成一个表格
        HSSFSheet sheet = wb.createSheet("注册人员数据");
        sheet.setDefaultColumnWidth(25);
        sheet.setDefaultRowHeightInPoints(20);
        //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数隶属有限制short
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，并设置表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//创建一个居中格式

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);

        cell=row.createCell(1);
        cell.setCellValue("手机号");
        cell.setCellStyle(style);

        cell=row.createCell(2);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);

        cell=row.createCell(3);
        cell.setCellValue("身份证号");
        cell.setCellStyle(style);

        cell=row.createCell(4);
        cell.setCellValue("推荐人");
        cell.setCellStyle(style);

        cell=row.createCell(5);
        cell.setCellValue("推荐人手机号");
        cell.setCellStyle(style);

        cell=row.createCell(6);
        cell.setCellValue("注册时间");
        cell.setCellStyle(style);

        //第五步，写入实体数据
        for(int i = 0;i<registerExcelList.size();i++){
            row = sheet.createRow(i+1);
            row.setHeightInPoints(20);
            Register datas = registerExcelList.get(i);
            //第六步，创建单元格，并设置值
            if(datas != null){
                //序号
                if(!String.valueOf(datas.getNumber()).equals("")){
                    cell = row.createCell(0);
                    cell.setCellValue(datas.getNumber());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(0);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }
                //手机号
                if(datas.getPhone() !=null && !datas.getPhone().equals("")){
                    cell = row.createCell(1);
                    cell.setCellValue(datas.getPhone());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(1);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }

                //姓名
                if(datas.getName() !=null && !datas.getName().equals("")){
                    cell = row.createCell(2);
                    cell.setCellValue(datas.getName());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(2);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }

                //身份证号
                if(datas.getIdCard() !=null && !datas.getIdCard().equals("")){
                    cell = row.createCell(3);
                    cell.setCellValue(datas.getIdCard());
                    cell.setCellStyle(style);
                }else{
                    cell = row.createCell(3);
                    cell.setCellValue("");
                    cell.setCellStyle(style);
                }

                //推荐人
                if(datas.getIntro_name() !=null && !datas.getIntro_name().equals("")){
                    cell = row.createCell(4);
                    cell.setCellValue(datas.getIntro_name());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(4);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }

                //推荐人手机号
                if(datas.getIntro_phone() !=null && !datas.getIntro_phone().equals("")){
                    cell = row.createCell(5);
                    cell.setCellValue(datas.getIntro_phone());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(5);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }

                //注册时间
                if(datas.getCt_date() !=null && !datas.getCt_date().equals("")){
                    cell = row.createCell(6);
                    cell.setCellValue(datas.getCt_date());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(6);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }
            }
        }
        return wb;
    }
    public HSSFWorkbook getJoinRegisterWb(List<Register> joinRegisterExcelList){
        //第一步，创建一个webbook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //第二步，在webbook中添加一个sheet，对应Excel文件中的sheet,生成一个表格
        HSSFSheet sheet = wb.createSheet("报名人员数据");
        sheet.setDefaultColumnWidth(25);
        sheet.setDefaultRowHeightInPoints(20);
        //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数隶属有限制short
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，并设置表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//创建一个居中格式

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);

        cell=row.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);

        cell=row.createCell(2);
        cell.setCellValue("手机号");
        cell.setCellStyle(style);

        cell=row.createCell(3);
        cell.setCellValue("报名时间");
        cell.setCellStyle(style);

        cell=row.createCell(4);
        cell.setCellValue("套餐");
        cell.setCellStyle(style);

        cell=row.createCell(5);
        cell.setCellValue("体验卡卡号");
        cell.setCellStyle(style);

        cell=row.createCell(6);
        cell.setCellValue("推荐人");
        cell.setCellStyle(style);

        cell=row.createCell(7);
        cell.setCellValue("推荐人手机号");
        cell.setCellStyle(style);

        for(int i = 0;i<joinRegisterExcelList.size();i++){
            row = sheet.createRow(i+1);
            row.setHeightInPoints(20);
            Register datas = joinRegisterExcelList.get(i);
            if(datas != null){
                //序号
                if(!String.valueOf(datas.getNumber()).equals("")){
                    cell = row.createCell(0);
                    cell.setCellValue(datas.getNumber());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(0);
                    cell.setCellValue("");
                    cell.setCellStyle(style);
                }
                //姓名
                if(datas.getName() !=null && !datas.getName().equals("")){
                    cell = row.createCell(1);
                    cell.setCellValue(datas.getName());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(1);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }

                //手机号
                if(datas.getPhone() !=null && !datas.getPhone().equals("")){
                    cell = row.createCell(2);
                    cell.setCellValue(datas.getPhone());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(2);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }

                //报名时间
                if(datas.getJoin_date() !=null && !datas.getJoin_date().equals("")){
                    cell = row.createCell(3);
                    cell.setCellValue(datas.getJoin_date());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(3);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }

                //套餐
                if(datas.getMeal_sort() !=null && !datas.getMeal_sort().equals("")){
                    cell = row.createCell(4);
                    cell.setCellValue(datas.getMeal_sort());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(4);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }

                //体验卡卡号
                if(datas.getExperience_card() !=null && !datas.getExperience_card().equals("")){
                    cell = row.createCell(5);
                    cell.setCellValue(datas.getExperience_card());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(5);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }
                //推荐人
                if(datas.getIntro_name() !=null && !datas.getIntro_name().equals("")){
                    cell = row.createCell(6);
                    cell.setCellValue(datas.getIntro_name());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(6);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }
                //推荐人手机号
                if(datas.getIntro_phone() !=null && !datas.getIntro_phone().equals("")){
                    cell = row.createCell(7);
                    cell.setCellValue(datas.getIntro_phone());
                    cell.setCellStyle(style);

                }else{
                    cell = row.createCell(7);
                    cell.setCellValue("");
                    cell.setCellStyle(style);

                }
            }
        }
        return wb;
    }
}
