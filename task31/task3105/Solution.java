package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/*
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {

        String fileName = args[0];
        String zipPath = args[1];

        ZipInputStream zip = new ZipInputStream(new FileInputStream(zipPath));
        Map<String, ByteArrayOutputStream> zipMap = new HashMap<>();

        ZipEntry zipEntry;
        while ((zipEntry = zip.getNextEntry()) != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = zip.read(buffer)) != -1)
                byteArrayOutputStream.write(buffer, 0, count);
            zipMap.put(zipEntry.getName(), byteArrayOutputStream);
        }
        zip.close();

        File file = new File(fileName);
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPath));
        for (Map.Entry<String, ByteArrayOutputStream> pair : zipMap.entrySet()) {
            if (pair.getKey().substring(pair.getKey().lastIndexOf("/") + 1).equals(file.getName())) continue;
            zipOutputStream.putNextEntry(new ZipEntry(pair.getKey()));
            zipOutputStream.write(pair.getValue().toByteArray());
        }

        zipEntry = new ZipEntry("new/" + file.getName());
        zipOutputStream.putNextEntry(zipEntry);
        Files.copy(file.toPath(), zipOutputStream);

        zipOutputStream.close();

    }
}
