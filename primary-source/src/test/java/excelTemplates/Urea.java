package excelTemplates;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class Urea {

    @ExcelProperty(value = "Biuret",index =0)
    @ColumnWidth(12)
    private double biuret;

    @ExcelProperty(value = "Water",index =1)
    @ColumnWidth(12)
    private double water;

    public void setBiuret(double biuret){
        this.biuret=biuret;
    }

    public void setWater(double water){
        this.water=water;
    }

    public double getBiuret(){

        return biuret;
    }

    public  double getWater(){

        return  water;
    }
}
