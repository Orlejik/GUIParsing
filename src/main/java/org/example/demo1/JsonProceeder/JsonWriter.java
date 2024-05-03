package org.example.demo1.JsonProceeder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonParser.Feature;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.example.demo1.JsonProceeder.JsonReader.readJsonFile;

public class JsonWriter {
    public static void writeMapToJSON(Map<String, Map<String, String>> hasmap, String fileToWrite){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        File file = new File(fileToWrite);
        try {

            if(!file.exists()){
                file.createNewFile();
            }else{
                Map<String, Map<String, String>> existingJSONContent = readJsonFile(fileToWrite);
                if(!isConflictPersists(existingJSONContent, hasmap)){
                    existingJSONContent.putAll(hasmap);
                    writer.writeValue(file, existingJSONContent);
                }else{
                    writer.writeValue(file, existingJSONContent);
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static boolean isConflictPersists(Map<String, Map<String, String>> existingMap,Map<String, Map<String, String>> newMap){
        for(String newOuterKey : newMap.keySet()){
            if(existingMap.containsKey(newOuterKey)){
                Map<String, String> newInnerMap = newMap.get(newOuterKey);
                Map<String, String> existingInnerMap = existingMap.get(newOuterKey);
                for(String newInnerKey : newInnerMap.keySet()){
                    if(existingInnerMap.containsKey(newInnerKey)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
