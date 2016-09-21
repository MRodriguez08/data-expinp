package com.mrdevelop.dataexpinp.utils;

import java.util.Map;

public interface Importable {
    
    /**
     * Map representation of the Importable, keys must have the same
     * name of the corresponding target column.
     * @return
     */
    public Map<String, Object> toMap();
    
}
