package com.javarush.task.task31.task3101;

import java.io.*;
import java.sql.Array;
import java.util.*;

/*
Проход по дереву файлов
*/
public class Solution {
    public static void main(String[] args) throws IOException {

        File path = new File(args[0]);
        File resultFileAbsolutePath = new File(args[1]);
        FileUtils.renameFile(resultFileAbsolutePath, new File(resultFileAbsolutePath.getParent() + "/allFilesContent.txt"));

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(args[1]).getParent() + "/allFilesContent.txt"));
        BufferedReader bufferedReader;

        // Work with files and subfiles in folder
        List<File> fileList = new ArrayList<>();

        fileList = getFiles(path);
        fileList.sort(Comparator.comparing(File::getName));

        for (File currentFile : fileList) {
            bufferedReader = new BufferedReader(new FileReader(currentFile));
            while (bufferedReader.ready()) {
                bufferedWriter.write(bufferedReader.readLine());
                bufferedWriter.write("\n");
            }
            bufferedReader.close();
        }
        bufferedWriter.close();


    }

    private static List<File> getFiles(File folder) {

        List<File> result = new ArrayList<>();

        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                for (File currnetFile : getFiles(file))
                    result.add(currnetFile);
            } else {
                if (file.length() <= 50) result.add(file);
            }
        }

        return result;
    }
}
