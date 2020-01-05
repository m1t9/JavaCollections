package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* 
Что внутри папки?
*/
public class Solution {

    static int foldersCount = -1;
    static int filesCount = 0;
    static int size = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Path path = Paths.get(reader.readLine());
        reader.close();

        if (!Files.isDirectory(path)) {
            System.out.println(path + " - не папка");
            return;
        }

        Files.walkFileTree(path, new fileVisitor());

        System.out.println("Всего папок - " + foldersCount);
        System.out.println("Всего файлов - " + filesCount);
        System.out.println("Общий размер - " + size);

    }

    public static class fileVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            foldersCount++;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            size += attrs.size();
            filesCount++;
            return FileVisitResult.CONTINUE;
        }
    }
}


