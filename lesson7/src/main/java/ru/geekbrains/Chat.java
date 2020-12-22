package ru.geekbrains;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Chat {

    Chat() {
        // авторизоваться
        // слушать консоль на предмет новых сообщений и отсылать их на сервер
        try {
            Socket socket = new Socket("localhost", 8081); // соккет для подключения к порту
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream()); // слушаем сообщения
            new Thread(() -> {
                try {
                    while (true) {
                        String newMessage = dataInputStream.readUTF(); // новое сообщение
                        System.out.println(newMessage);// выводим сообщения в лог
                    }
            } catch (IOException e) {
                    e.printStackTrace();
            }
            }).start();
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            new Thread(() -> {
                try {
                    while(true) {
                        String newMessage = scanner.nextLine(); // читаем сканер на предмет новой строки
                        dataOutputStream.writeUTF(newMessage); // отсылаем строку на сервер
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Chat(); // запускаем наш чат
    }
}
