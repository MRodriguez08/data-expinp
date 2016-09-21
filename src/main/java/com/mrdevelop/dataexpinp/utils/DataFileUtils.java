package com.mrdevelop.dataexpinp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataFileUtils {
    
    public static String[] getFieldsNamesFromFile(File file) throws FileNotFoundException, IOException{
        
        String[] columns = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            line = br.readLine();
            if (line != null){
                columns = line.split("\\,");
            }
        }
        return columns;
    }

}
