package com.javarush.task.task32.task3210;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {

        String fileName = args[0];
        Long number = Long.parseLong(args[1]);
        String text = args[2];

        RandomAccessFile file = new RandomAccessFile(fileName, "rw");
        file.seek(number);
        byte b[] = new byte[text.length()];
        file.read(b, 0, text.length());
        file.seek(file.length());
        String string = new String(b);
        if (text.equals(string)) {
            file.write("true".getBytes());
        } else {
            file.write("false".getBytes());
        }

    }
}
