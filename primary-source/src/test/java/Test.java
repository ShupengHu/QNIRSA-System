import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.WriteSheet;
import dataManager.ExcelManager;

import dataManager.DataProcessor;
import excelTemplates.Model;
import excelTemplates.Urea;
import excelTemplates.Wavelength;
import methodsLibrary.SG_JAVA;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
    private static String filePath="G:\\Professional Software\\IntelliJ IDEA\\My Projects\\QNIRSA System\\primary-source\\src\\test\\resources\\ExcelDocuments\\";

    public static void main(String[] args) throws Exception {

        testExcel();
    }

    public  static void testMethod() throws Exception {
        List<double[]> list1=new ArrayList<double[]>();
        List<Object> list2=new ArrayList<Object>();
        List<double[]> list3=new ArrayList<double[]>();
        list1= ExcelManager.readExcel(filePath+"Spectra-SRK.xlsx");
        double[][] data= DataProcessor.listToDoubleArray1(list1);
        System.out.println(data[0][0]);
        list2=ExcelManager.readExcel(filePath+"Wavelength-Rice.xlsx", Wavelength.class);
        double[][] wavelength= DataProcessor.listToDoubleArray2(list2,"Wavelength");
        System.out.println(wavelength[0][0]);

        SG_JAVA sg_java=new SG_JAVA();
        sg_java.setParameter(7,1,1);
        sg_java.setWavelength(wavelength);
        sg_java.invokeMethod(data);
        sg_java.parseResult();
        double[][] result=sg_java.getResult();
        ExcelManager.writeExcel(filePath+"afterPreProcess.xlsx",result);
    }

    public static void testExcel() throws Exception {
        List<Object> list=new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime=df.format(new Date());
        dateTime=dateTime.replaceAll(":", "-");
        for(double i=1;i<4;i++){
            Urea urea =new Urea();
            urea.setWater(i);
            urea.setBiuret(i+1.1);
            list.add(urea);
        }
        WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
        ExcelManager excelManager=new ExcelManager();
        excelManager.repeatWriteExcel(filePath+dateTime+" Urea.xlsx",list,Urea.class,writeSheet);
        for(double i=1;i<5;i++){
            Urea urea =new Urea();
            urea.setWater(i);
            urea.setBiuret(i+1.1);
            list.add(urea);
        }
        excelManager.repeatWriteExcel(filePath+dateTime+" Urea.xlsx",list,Urea.class,writeSheet);

    }
}
