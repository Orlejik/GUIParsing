package org.example.demo1.FileCreators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.example.demo1.FileCreators.ChooseTheFile.chooseFile;


public class WriteInFile {

    private String filePath;

    public WriteInFile(String filePath) {
        this.filePath = filePath;
    }

    public void writeInFile(String lineToWriteInFile) throws IOException {
        File file = new File(this.filePath);
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
