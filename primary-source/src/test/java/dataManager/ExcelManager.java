package dataManager;

import com.alibaba.excel.EasyExcel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelManager {
    private List<Object> listO=new ArrayList<Object>();
    private List<double[]> listD=new ArrayList<double[]>();

    /**
     * Import data from excel with template
     * @param fileName
     * @param c template class
     * @throws Exception
     */
    public void readExcel(String fileName, Class c) throws Exception{
        ExcelListener el=new ExcelListener();
        EasyExcel.read(fileName, c, el).sheet().doRead();
        listO = el.getList();
    }

    /**
     * Import data from excel without template
     * @param fileName
     * @throws Exception
     */
    public void readExcel(String fileName) throws Exception{
        InputStream is=new FileInputStream(fileName);
        XSSFWorkbook excel=new XSSFWorkbook(is);
        XSSFSheet sheet=excel.getSheetAt(0);
        listD =new ArrayList<double[]>();
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
            listD.add(d);
        }
        is.close();
    }

    /**
     * Export data to Excel with template
     * @param fileName
     * @param list data
     * @param c template class
     * @throws Exception
     */
    public void writeExcel (String fileName, List<Object> list,Class c) throws Exception{
        EasyExcel.write(fileName, c).sheet().doWrite(list);
    }

    /**
     * Export data to Excel without template
     * @param fileName
     * @param list data
     * @throws Exception
     */
    public void writeExcel (String fileName, List<double[]> list) throws Exception{
        File file= new File(fileName);
        if (file.exists()==false){
            file.createNewFile();
        }
        XSSFWorkbook excel=new XSSFWorkbook();
        XSSFSheet sheet=excel.createSheet();
        /**
         * Loop for exporting data to Excel
         */
        for(int i=0;i<list.size();i++){
            XSSFRow row=sheet.createRow(i);
            for(int j=0;j<list.get(i).length;j++){
                XSSFCell cell=row.createCell(j);
                cell.setCellValue(list.get(i)[j]);
            }
        }

        OutputStream os =new FileOutputStream(file);
        excel.write(os);
        os.close();
    }

    public List<Object> getListO(){

        return listO;
    }

    public List<double[]> getListD(){

        return listD;
    }
}
