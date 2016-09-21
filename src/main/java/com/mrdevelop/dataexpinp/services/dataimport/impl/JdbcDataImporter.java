package com.mrdevelop.dataexpinp.services.dataimport.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrdevelop.dataexpinp.services.dataimport.DataImportService;
import com.mrdevelop.dataexpinp.utils.Importable;
import com.mrdevelop.dataexpinp.utils.dataimport.jdbc.NamedParameterStatement;
import com.mrdevelop.dataexpinp.utils.exceptions.InvalidTargetConfigurationException;

public class JdbcDataImporter<T extends Importable> implements DataImportService<T> {

    public static final Logger LOGGER = LoggerFactory.getLogger(JdbcDataImporter.class);

    @Override
    public void importData(Map<String, String> targetData, List<T> dataItems)
            throws InvalidTargetConfigurationException {
        // mysql ie: jdbc:mysql://localhost:3306/mkyongcom
        // postgres ie: TODO
        String db_url = targetData.get("db.url") + "?useServerPrepStmts=false&rewriteBatchedStatements=true";

        // ie: root
        String db_username = targetData.get("db.username");

        // ie: ms_admin
        String db_password = targetData.get("db.password");

        // [mysql | postgres]
        String db_type = targetData.get("db.type");

        // [mysql | postgres]
        String db_table = targetData.get("rbb.table");

        loadJdbcDriver(db_type);

        Connection connection = null;
        String insertTableSQL = createInsertStatement(db_table, dataItems.get(0).toMap());
        try {
            connection = DriverManager.getConnection(db_url, db_username, db_password);
            NamedParameterStatement statement = new NamedParameterStatement(connection, insertTableSQL);
            
            final int batchSize = 100;
            int count = 0;
            
            for (Importable i : dataItems){
                Map<String, Object> mapRepr = i.toMap();
                for (String k : mapRepr.keySet()){
                    //TODO support multiple types
                    statement.setString(k, String.valueOf(mapRepr.get(k)));
                }
                
                statement.addBatch();
                
                if(++count % batchSize == 0) {
                    statement.executeBatch();
                    LOGGER.debug("processed {} items...", count);
                }
            }
            
            statement.executeBatch(); // insert remaining records
            statement.close();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

    }

    private void loadJdbcDriver(String db_type) throws InvalidTargetConfigurationException {
        try {
            if ("mysql".equals(db_type)) {
                Class.forName("com.mysql.jdbc.Driver");
            } else {
                throw new ClassNotFoundException("Invalid db type");
            }
            LOGGER.debug("MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            LOGGER.error("could not find driver {}");
            throw new InvalidTargetConfigurationException("could not find driver " + e.getMessage());
        }
    }
    
    private String createInsertStatement(String table, Map<String, Object> items){
        StringBuilder insertStatement =  new StringBuilder();
        
        String columns = StringUtils.join(items.keySet(), ",");
        insertStatement.append("INSERT INTO ").append(table).append("(").append(columns).append(") VALUES (");
        
        int i = 0;
        for (String k : items.keySet()){
            insertStatement.append(":"+k);
            if (i+1 < items.entrySet().size()){
                insertStatement.append(",");
            }
            i++;
        }
        insertStatement.append(")");
        LOGGER.debug("resulting sql query: {}", insertStatement.toString());
        return insertStatement.toString();
    }

}
