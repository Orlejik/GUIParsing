package org.example.demo1.Validation;

import javafx.scene.control.TextField;

import java.util.List;

public class isPathValid {
    private boolean isValid;
    static int result = 0;
    public static boolean checkPath(List<TextField> textFieldsList){
        for(TextField textField : textFieldsList){
            if(!textField.getText().isEmpty()){
                result+=1;
            }else{
                result-=1;
            }
        }
        return result == 4;
    }
}
