package ru.geekbrains;

import java.util.HashMap;
import java.util.HashSet;

public class PhoneBook {
    HashMap<String, HashSet<String>> book;

    public PhoneBook() {
        this.book = new HashMap<>();
    }

    public void Add(String name, String phone) {
        HashSet<String> phonebook = book.getOrDefault(name, new HashSet<>());
        phonebook.add(phone);
        book.put(name, phonebook);
    }

    public void get(String name) {
        System.out.println(name + " / тел. номер " + book.getOrDefault(name, new HashSet<>()));
    }
}




