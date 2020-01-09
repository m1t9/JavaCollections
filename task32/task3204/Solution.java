package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String password = getRandomPassword();
        while (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$")) {
            password = getRandomPassword();
        }
        byteArrayOutputStream.write(password.getBytes());
        return byteArrayOutputStream;
    }

    public static String getRandomPassword() {
        return new Random().ints(48, 123)
                .filter(i -> (i <= 57 || (i >= 65 && i <= 90) || i >= 97))
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}