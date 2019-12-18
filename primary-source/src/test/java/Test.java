import dataManager.ExcelManager;
import excelTemplates.Wavelength;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {

        String filePath="G:\\Professional Software\\IntelliJ IDEA\\My Projects\\QNIRSA-System\\primary-source\\src\\test\\resources\\ExcelDocuments\\";
        ExcelManager em=new ExcelManager();
        List<Object> list=new ArrayList<Object>();
        em.readExcel(filePath+"Wavelength-Rice.xlsx",Wavelength.class);
        list=em.getListO();
        System.out.println(list);
        System.out.println(((Wavelength) list.get(0)).getWavelength());
        //List<Double[]> list=new ArrayList<Double[]>();
        //em.readExcel(filePath+"Spectra-SRK.xlsx");
        //list=em.getListD();
        //em.writeExcel(filePath+"1.xlsx",list);



    }
}
