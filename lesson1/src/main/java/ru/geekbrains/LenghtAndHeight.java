package ru.geekbrains;

public abstract class LenghtAndHeight {

    public int lenght;
    private int height;
    private String name;

    public LenghtAndHeight(String name, int lenght, int height) {
        this.name = name;
        this.lenght = lenght;
        this.height = height;
    }

    public int getLenght() {
        return lenght;
    }

    public int getHeight() {
        return height;
    }

}
