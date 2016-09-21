package com.mrdevelop.dataexpinp.services.datasource.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrdevelop.dataexpinp.services.datasource.DataSourceService;
import com.mrdevelop.dataexpinp.utils.Importable;
import com.mrdevelop.dataexpinp.utils.exceptions.DataSourceUnreachableException;
import com.mrdevelop.dataexpinp.utils.exceptions.MissingRequiredParamsException;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public abstract class CsvDataSource<T extends Importable> implements DataSourceService<T> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvDataSource.class);
    
    private ParameterizedType genericSuperclass;
    
    public CsvDataSource(){
        genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    }

    /**
     * Creates a list of entities from the given csv file
     * @param params[0] csv file path
     */
    public List<T> getDataSourceItems(Object... params) throws DataSourceUnreachableException, MissingRequiredParamsException {
        try {
            
            if (params == null){
                throw new MissingRequiredParamsException("");
            }
            
            ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
            strat.setType((Class<T>)genericSuperclass.getActualTypeArguments()[0]);
            String[] columns = (String[])params[1]; // the fields to bind do in your JavaBean
            strat.setColumnMapping(columns);

            CsvToBean csv = new CsvToBean();
            List<T> list = csv.parse(strat, createReader(String.valueOf(params[0])));
           
            return list;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new DataSourceUnreachableException("");
        }
    }
    
    private CSVReader createReader(String filePath) throws FileNotFoundException{
        CSVReader reader = new CSVReader(new FileReader(filePath));
        return reader;
    }

}
