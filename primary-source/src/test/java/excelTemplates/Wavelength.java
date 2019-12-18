package excelTemplates;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class Wavelength {

    @ExcelProperty(value = "Wavelength",index =0)
    @ColumnWidth(15)
    private  double wavelength;

    public double getWavelength(){

        return  wavelength;
    }
}
