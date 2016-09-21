package com.mrdevelop.dataexpinp.services.dataimport;

import java.util.List;
import java.util.Map;

import com.mrdevelop.dataexpinp.utils.exceptions.InvalidTargetConfigurationException;

public interface DataImportService<T> {
    
    public void importData(Map<String, String> targetData, List<T> dataItems) throws InvalidTargetConfigurationException;

}
