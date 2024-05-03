package org.example.demo1.Helpers;

public class ClearString {
    public static String clearString(String str, char character, int plusFromStartstart){
        int delimiterIndex = str.indexOf(character);

        if (delimiterIndex != -1) {
            String cutString = str.substring(delimiterIndex+plusFromStartstart);
            return cutString;

        } else {
            return "null!";
        }
    }
}
