package org.example.demo1.Validation;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;

import java.util.List;

import static org.example.demo1.Validation.isPathValid.checkPath;

public class InputsValidation {

    public static void validateInput(TextField textField, Label label, Button btn, List<TextField> textFieldsList){
        if(!textField.getText().matches("\\d+")){
            label.setText("Input should contain only numbers!");
            if(!checkPath(textFieldsList)){
                btn.setDisable(true);
            }
        }else if (textField.getLength()<8 || textField.getLength()>8 ){
//        }else if (!textField.getText().matches("\\d[8]]")){
            label.setText("The length should be 8 characters");
            if(!checkPath(textFieldsList)){
                btn.setDisable(true);
            }
        } else if (textField.getText().equals("") ) {
            label.setText("the input in empty.... ");
            if(!checkPath(textFieldsList)){
                btn.setDisable(true);
            }
        } else if (textField.getLength()>8) {
            textField.setBackground(Background.EMPTY);
        } else{
            label.setText("");
            btn.setDisable(false);
        }
    }

}
