package excelTemplates;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class Rice {

    @ExcelProperty(value = "Protein",index =0)
    @ColumnWidth(12)
    private double protein;

    public double getProtein(){

        return protein;
    }
}
