package org.example.demo1.JsonProceeder;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonReader {
    public static Map<String, Map<String, String>> readJsonFile(String fileName){
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.readValue(new File(fileName), Map.class);
        }catch(IOException ex){
            ex.printStackTrace();
            return new HashMap<>();
        }
    }
}
