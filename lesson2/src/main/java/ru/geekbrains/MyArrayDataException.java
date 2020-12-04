package ru.geekbrains;

public class MyArrayDataException extends RuntimeException {
    int row;
    int column;

    MyArrayDataException(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
