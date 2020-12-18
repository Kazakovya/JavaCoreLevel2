package ru.geekbrains;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public ChatServer() {
        ServerSocket server = null;
        Socket socket = null;
        try {
            server = new ServerSocket(8000);
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = server.accept();
            System.out.println("Клиент подключился");
            new Client(socket, "Сервер");
            while (true) {
                if (socket.isClosed()) {
                    break;
                }
            }
            server.close();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Ошибка инициализации сервера");
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new ChatServer();
    }
}
