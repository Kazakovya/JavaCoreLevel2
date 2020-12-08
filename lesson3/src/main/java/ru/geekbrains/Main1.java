package ru.geekbrains;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        PhoneBook book = new PhoneBook();
        book.Add("Иванов", "9603412354");
        book.Add("Петров", "9273145686");
        book.Add("Сидоров", "9053241258");
        book.Add("Иванов", "9875243698");
        book.Add("Петров", "9375486987");
        book.Add("Сидоров", "9032456897");

        book.get("Иванов");
        book.get("Петров");
        book.get("Сидоров");

    }
}