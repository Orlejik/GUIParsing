package org.example.demo1.FileCreators;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;

public class ChooseTheFile {
    public static void chooseFile(TextField textField, String baseFileName,String extension, Circle circle){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Safe "+extension+" File");
        fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
        fileChooser.setInitialFileName(baseFileName);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files (*."+extension+")", "."+extension));

        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null){
            textField.setText(file.getAbsolutePath());
            circle.setFill(Color.GREEN);
        }else{
            textField.setText("");
            circle.setFill(Color.RED);
        }
    }
}
