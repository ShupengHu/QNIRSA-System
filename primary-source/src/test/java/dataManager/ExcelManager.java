package dataManager;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelManager {

    /**
     * Import data from excel with template
     * @param fileName
     * @param c template class
     * @throws Exception
     * @return
     */
    public static List<Object> readExcel(String fileName, Class c) throws Exception{
        List<Object> list=new ArrayList<Object>();
        ExcelListener el=new ExcelListener();
        EasyExcel.read(fileName, c, el).sheet().doRead();
        list = el.getList();
        return list;
    }

    /**
     * Import data from excel without template
     * @param fileName
     * @throws Exception
     * @return
     */
    public static List<double[]> readExcel(String fileName) throws Exception{
        List<double[]> list =new ArrayList<double[]>();
        InputStream is=new FileInputStream(fileName);
        XSSFWorkbook excel=new XSSFWorkbook(is);
        XSSFSheet sheet=excel.getSheetAt(0);
        /**
         *loop for extracting data from excel
         *Example: excel size= 201x936. sheet.getLastRowNum()=200; row.getLastCellNum()=936
         */
        for (int i=0;i<sheet.getLastRowNum()+1;i++){
            XSSFRow row=sheet.getRow(i);
            double[] d=new double[row.getLastCellNum()];
            for(int j=0;j<row.getLastCellNum();j++){
                XSSFCell cell=row.getCell(j);
                d[j]=cell.getNumericCellValue();
            }
            list.add(d);
        }
        is.close();
        return list;
    }

    /**
     * Export data to Excel with template
     * @param fileName
     * @param list data
     * @param c template class
     * @throws Exception
     */
    public static void writeExcel (String fileName, List<Object> list,Class c) throws Exception{
        EasyExcel.write(fileName, c).sheet().doWrite(list);
    }

    /**
     * Export data to Excel without template
     * @param fileName
     * @param data
     * @throws Exception
     */
    public static void writeExcel (String fileName, double[][] data) throws Exception{
        File file= new File(fileName);
        if (file.exists()==false){
            file.createNewFile();
        }
        XSSFWorkbook excel=new XSSFWorkbook();
        XSSFSheet sheet=excel.createSheet();
        /**
         * Loop for exporting data to Excel
         */
        for(int i=0;i<data.length;i++){
            XSSFRow row=sheet.createRow(i);
            for(int j=0;j<data[0].length;j++){
                XSSFCell cell=row.createCell(j);
                cell.setCellValue(data[i][j]);
            }
        }

        OutputStream os =new FileOutputStream(file);
        excel.write(os);
        os.close();
    }

    /**
     * Export data to Excel without template and won't cover previous data in the Excel document
     * @param fileName
     * @param list
     * @param c
     */
    public static void repeatWriteExcel(String fileName,List<Object> list, Class c, WriteSheet writeSheet){
        ExcelWriter excelWriter = EasyExcel.write(fileName, c).build();
        // 这里注意 如果同一个sheet只要创建一次
        excelWriter.write(list, writeSheet);
        excelWriter.finish();
    }

}
