package com.javarush.task.task25.task2515;

public class Canvas {
    private int width, height;
    private char[][] matrix;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new char[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public void setPoint(double x, double y, char c) {
        int roundX, roundY;
        if (x < 0 || y <0 || y >= this.matrix.length || x >= this.matrix[0].length) return;
        roundX = (int) Math.round(x);
        roundY = (int) Math.round(y);
        this.matrix[roundY][roundX] = c;
    }

    public void drawMatrix(double x, double y, int[][] matrix, char c) {
        for (int j = 0; j < matrix.length; j++) {
            for (int i = 0; i < matrix[0].length; i++) {
                 if (matrix[j][i] != 0) {
                     setPoint(x + i, y + j, c);
                 }
            }
        }
    }
}
