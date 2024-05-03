package org.example.demo1.FileCreators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class CreateFile {
    public static void writeInFile(String filePath, String lineToWriteInFile) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            newFile(file, lineToWriteInFile);
        }
    }

    protected static void newFile(File file, String content) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(content);
            writer.flush();
        }
    }
}
