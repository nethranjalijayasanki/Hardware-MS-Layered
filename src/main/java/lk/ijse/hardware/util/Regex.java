package lk.ijse.hardware.util;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(lk.ijse.hardware.util.TextField textField, String text){
        String filed = "";

        switch (textField){
            case NAME:
                filed = "^[A-z|\\\\s]{3,}$";
                break;
            case EMAIL:
                filed = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case TEL:
                filed = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                break;
            case SALARY:
                filed =  "^([0-9]){1,}[.]([0-9]){1,}$";
                break;
            case QTY:
                filed = "^\\d+$";
                break;
            case PRICE:
                filed =  "^([0-9]){1,}[.]([0-9]){1,}$";
                break;
        }

        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(lk.ijse.hardware.util.TextField location, TextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setStyle("-fx-text-fill: Green; ");
        }else {
            textField.setStyle("-fx-text-fill: red;");
            return false;
        }
        return false;
    }
}
