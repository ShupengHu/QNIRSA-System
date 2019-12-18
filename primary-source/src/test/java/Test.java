import dataManager.ExcelManager;

import dataProcessor.PreProcessor;
import excelTemplates.Wavelength;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {

        String filePath="G:\\Professional Software\\IntelliJ IDEA\\My Projects\\QNIRSA System\\primary-source\\src\\test\\resources\\ExcelDocuments\\";
        ExcelManager em=new ExcelManager();
        //List<Object> list=new ArrayList<Object>();
        //em.readExcel(filePath+"Wavelength-Rice.xlsx",Wavelength.class);
        //list=em.getListO();
        //System.out.println(list);
        //System.out.println(((Wavelength) list.get(0)).getWavelength());
        List<double[]> list=new ArrayList<double[]>();
        em.readExcel(filePath+"Spectra-SRK.xlsx");
        list=em.getListD();
        //em.writeExcel(filePath+"1.xlsx",list);

        double[][] data= new double[list.size()][list.get(0).length];
        for (int i=0; i< list.size();i++){
            data[i]=list.get(i);
        }
        PreProcessor preProcessor=new PreProcessor();
        double[][] afterPreProcess=preProcessor.preProcess("SNV",data);
        List<double[]> l=new ArrayList<double[]>();
        for (int i=0; i< list.size();i++){
            l.add(afterPreProcess[i]);
        }
        em.writeExcel(filePath+"afterPreProcess.xlsx",l);

    }
}
