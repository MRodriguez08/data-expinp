package com.mrdevelop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrdevelop.dataexpinp.sample.csvtojdbc.model.User;
import com.mrdevelop.dataexpinp.sample.csvtojdbc.model.UserDataImporter;
import com.mrdevelop.dataexpinp.sample.csvtojdbc.model.UserDataSource;
import com.mrdevelop.dataexpinp.services.datasource.DataSourceService;
import com.mrdevelop.dataexpinp.services.datasource.impl.CsvDataSource;
import com.mrdevelop.dataexpinp.utils.Importable;
import com.mrdevelop.dataexpinp.utils.exceptions.DataSourceUnreachableException;
import com.mrdevelop.dataexpinp.utils.exceptions.InvalidTargetConfigurationException;
import com.mrdevelop.dataexpinp.utils.exceptions.MissingRequiredParamsException;

/**
 * Hello world!
 *
 */
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args)  {
        LOGGER.info("-------- starting jdbc sample data loading ------------");

        // mysql ie: jdbc:mysql://localhost:3306/mkyongcom
        // postgres ie: TODO
        String db_url = System.getProperty("db.url");
        db_url = "jdbc:mysql://localhost:3306/elasticsearchtraining";

        // ie: root
        String db_username = System.getProperty("db.username");
        db_username = "root";

        // ie: ms_admin
        String db_password = System.getProperty("db.password");
        db_password = "ms_admin";

        // [mysql | postgres]
        String db_type = System.getProperty("db.type");
        db_type = "mysql";
        
     // [mysql | postgres]
        String db_table = System.getProperty("rbb.table");
        db_table = "users";
        
        // ie : /home/myuser/data/mockdata.csv
        String mockdata = System.getProperty("mockdata");
        
        //Integer importIterations = Integer.valueOf(System.getProperty("mockdata"));
        Integer importIterations = 1;
        
        UserDataSource userDataSource = new UserDataSource();
        UserDataImporter userDataImporter = new UserDataImporter();
        
        try {
            
            long startTime = System.currentTimeMillis();
            List<User> items = userDataSource.getDataSourceItems("/home/mauriciorodriguez/Downloads/MOCK_DATA.csv", User.SOURCE_FIELDS);
            long endTime = System.currentTimeMillis();
            LOGGER.info("loading took {} milliseconds.", (endTime - startTime));
            
            for (User u : items){
                //LOGGER.debug("UserName: {}", u.getFirst_name());
            }
            
            Map<String, String> targetData = new HashMap<String, String>();
            targetData.put("db.url", db_url);
            targetData.put("db.username", db_username);
            targetData.put("db.password", db_password);
            targetData.put("db.type", db_type);
            targetData.put("rbb.table", db_table);
            
            startTime = System.currentTimeMillis();
            for (Integer i = 0; i < importIterations; i++){
                userDataImporter.importData(targetData, items);
                LOGGER.debug("iteration {} of {}", i+1, importIterations);
            }
            endTime = System.currentTimeMillis();
            LOGGER.info("importing took {} milliseconds.", (endTime - startTime));
        } catch (DataSourceUnreachableException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (MissingRequiredParamsException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InvalidTargetConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
        }
        
    }
}
