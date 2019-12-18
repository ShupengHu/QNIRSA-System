package dataManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileManager {

    /**
     * Export data to text file
     * @param data 数据
     * @param fileName 记事本文件名
     * @throws IOException
     */
    public void writeText(double[] data, String fileName) throws IOException{
        //create text file if it is nonexistent
        File file=new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        //export data
        FileWriter FW=new FileWriter(file);
        for(double d:data){
            FW.write(d+"\r\n");
        }
        FW.close();
    }




}
