package ru.geekbrains;

public class Message { // создаём конструктор сообщений
    Client client;
    String text;

    public Message(Client client, String text) {
        this.client = client;
        this.text = text;
    }
}
