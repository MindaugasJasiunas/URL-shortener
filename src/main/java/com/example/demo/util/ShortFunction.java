package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.util.Random;

public class ShortFunction {
    private static final String ALPHABET= "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
    private static final int SIZE= ALPHABET.length();
    private static final Random random= new Random();

    public static String generate() {
        //encoding link to 6 symbols - 965660736 possible strings
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<6;i++){
            sb.append(ALPHABET.charAt(random.nextInt(SIZE)));
        }
        return sb.toString();
    }



}
