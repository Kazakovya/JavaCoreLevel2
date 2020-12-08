package ru.geekbrains;


import java.util.*;

public class Repeat {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("рука", "нога", "голова", "спина", "колено",
                "плечо", "рука", "нога", "голова", "спина", "колено", "плечо", "колено", "голова"));

        Set<String> unique = new HashSet<>(list);

        System.out.println("Первоначальный массив слов");
        System.out.println(list.toString());
        System.out.println("Уникальные слова");
        System.out.println(unique.toString());
        System.out.println("Частота повторения слов");
        for (String key : unique) {
            System.out.println(key + ": " + Collections.frequency(list, key));
        }
    }
}
