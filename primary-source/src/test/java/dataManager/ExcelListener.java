package dataManager;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener extends AnalysisEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelListener.class);
    private List<Object> list= new ArrayList<Object>();  //store data from Excel

    /**
     * below variables are used when database is required
    private static final int BATCH_COUNT = 100;     //store data into database per 100 data elements，then clear list to save memory
    private DBManager dbm;    // object for database manager
     */
    public ExcelListener() {
          //dbm= new DBManager();
    }

    /**
     * parse data from excel row by row
     * @param o   one row value
     * @param context
     */
    public void invoke(Object o, AnalysisContext context) {
        list.add(o);  //store data

        /**
         * reach the threshold，store data into database，avoid OOM exception
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // clear list after storage in database
            list.clear();
        }
         */
    }

    /**
     * actions after all data parsed
     * @param context
     */
    public void doAfterAllAnalysed(AnalysisContext context) {
        /**
         *store remaining data into database
        saveData();
         */
        LOGGER.info("All data parsed！");
    }

    /**
     * save data into database
     */
    private void saveData() {
        LOGGER.info("{} data elemetns into database！", list.size());
        //dbm.save(list);
        LOGGER.info("data stored successfully！");
    }

    public List<Object> getList(){

        return list;
    }
}
