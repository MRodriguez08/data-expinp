package com.mrdevelop.dataexpinp.services.datasource;

import java.util.List;

import com.mrdevelop.dataexpinp.utils.Importable;
import com.mrdevelop.dataexpinp.utils.exceptions.DataSourceUnreachableException;
import com.mrdevelop.dataexpinp.utils.exceptions.MissingRequiredParamsException;

public interface DataSourceService<T extends Importable> {
    
    public List<T> getDataSourceItems(Object... params) throws DataSourceUnreachableException, MissingRequiredParamsException;

}
