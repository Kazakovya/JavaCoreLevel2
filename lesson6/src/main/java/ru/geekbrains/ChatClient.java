package ru.geekbrains;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8000;

    public ChatClient() {
        try {
            Socket socket = new Socket(SERVER_ADDR, SERVER_PORT);
            new Client(socket, "Клиент");
            while (true) {
                if (socket.isClosed()) {
                    break;
                }
            }
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatClient();
    }
}
