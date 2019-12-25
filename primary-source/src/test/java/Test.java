import dataManager.ExcelManager;

import dataManager.DataProcessor;
import excelTemplates.Wavelength;
import methodsLibrary.SG_JAVA;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {

        String filePath="G:\\Professional Software\\IntelliJ IDEA\\My Projects\\QNIRSA System\\primary-source\\src\\test\\resources\\ExcelDocuments\\";
        ExcelManager em=new ExcelManager();
        List<double[]> list1=new ArrayList<double[]>();
        List<Object> list2=new ArrayList<Object>();
        List<double[]> list3=new ArrayList<double[]>();

        em.readExcel(filePath+"Spectra-SRK.xlsx");
        list1=em.getListD();
        double[][] data= DataProcessor.listToDoubleArray1(list1);
        System.out.println(data[0][0]);
        em.readExcel(filePath+"Wavelength-Rice.xlsx", Wavelength.class);
        list2=em.getListO();
        double[][] wavelength= DataProcessor.listToDoubleArray2(list2,"Wavelength");
        System.out.println(wavelength[0][0]);

        SG_JAVA sg_java=new SG_JAVA();
        sg_java.setParameter(7,1,1);
        sg_java.setWavelength(wavelength);
        sg_java.invokeMethod(data);
        sg_java.parseResult();
        double[][] result=sg_java.getResult();
        System.out.println(result[0][3]);
        list3= DataProcessor.doubleArrayToList1(result);
        System.out.println(list3.get(0)[0]);
        em.writeExcel(filePath+"afterPreProcess.xlsx",list3);

    }
}
