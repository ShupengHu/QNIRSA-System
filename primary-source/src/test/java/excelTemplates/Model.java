package excelTemplates;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class Model {

    @ExcelProperty(value = "Regression Coefficients",index =0)
    @ColumnWidth(25)
    private double coefficients;

    public void setCoefficients(double coefficients){
        this.coefficients=coefficients;
    }

    public double getCoefficients(){

        return coefficients;
    }

}
