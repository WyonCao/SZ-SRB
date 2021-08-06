package com.wyon.easyexcel.testEasyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.wyon.easyexcel.listener.ExcelStudentDTOListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EasyExcelTest {

    private String url="D:/easyExc/simple.xlsx";
    //读操作
    /**
     * 最简单的读
     */
    @Test
    public void simpleReadXlsx() {

        String fileName = "d:/excel/simpleWrite.xlsx";
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, ExcelStudentDTO.class, new ExcelStudentDTOListener()).sheet().doRead();
    }

    @Test
    public void simpleReadXls() {

        String fileName = "d:/excel/simpleWrite.xls";
        EasyExcel.read(fileName, ExcelStudentDTO.class, new ExcelStudentDTOListener()).excelType(ExcelTypeEnum.XLS).sheet().doRead();
    }







    //写操作
    @Test
    public void querySheet(){

        //从数据库中查询数据到DTO中
        List<ExcelStudentDTO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelStudentDTO excelStudentDTO = new ExcelStudentDTO();

            excelStudentDTO.setName("学生姓名"+i);
            excelStudentDTO.setSalary(1000d+i);
            excelStudentDTO.setBirthday(new Date());

            list.add(excelStudentDTO);

        }
        //再将DTO写到Excel中
        EasyExcel.write("D:/easyExc/simple.xlsx",ExcelStudentDTO.class).sheet("学生信息").doWrite(list);


    }
}
