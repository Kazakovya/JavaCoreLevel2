package ru.geekbrains;

public class Main {
    static final int SIZE = 4;

    public static void main(String[] args) {
        String[][] matrix = new String[][]{
                {"0", "1", "2", "3"},
                {"0", "1", "2", "3"},
                {"0", "1", "2", "3"},
                {"0", "1", "2", "3"}
        };
        try {
            checkSizeSum(matrix);
        } catch (MyArraySizeException e) {
            System.out.println("Matrix not 4x4");
        } catch (MyArrayDataException e) {
            System.out.println("Not a number in " + e.row + " " + e.column);
        }

    }

    public static void checkSizeSum(String[][] matrix) {
        if (matrix.length != SIZE) throw new MyArraySizeException();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != SIZE) throw new MyArraySizeException();
        }
        int sum = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                try {
                    sum += Integer.parseInt(matrix[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j);
                }
            }
        }
        System.out.println("Сумма элементов массива " + sum);
    }
}
