package com.javarush.task.task31.task3106;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) throws IOException {

        ZipInputStream zipInputStream;

        List<String> fileNames = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            fileNames.add(args[i]);
        }
        Collections.sort(fileNames);

        List<FileInputStream> files = new ArrayList<>();
        for (String name : fileNames)
            files.add(new FileInputStream(name));

        SequenceInputStream sequenceInputStream = new SequenceInputStream(Collections.enumeration(files));

        zipInputStream = new ZipInputStream(sequenceInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(args[0]);
        byte[] buffer = new byte[1024];
        int reader = 0;
        while (zipInputStream.getNextEntry() != null) {
            while ((reader = zipInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, reader);
            }
        }

        zipInputStream.close();
        fileOutputStream.close();
        sequenceInputStream.close();

    }
}
