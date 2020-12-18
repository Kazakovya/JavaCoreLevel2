package ru.geekbrains;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class Client {
    private Scanner in;
    private Scanner input;
    private PrintWriter out;
    private Thread threadIn;
    private Thread threadOut;

    public Client(Socket socket, String name) {
        try {
            in = new Scanner(socket.getInputStream());
            input = new Scanner(System.in);
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        threadOut = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (input.hasNext()) {
                        String q = input.next();
                        sendMsg(name + ": " + q);
                        if (q.equalsIgnoreCase("end")) break;
                    }
                }
                close(socket);

            }
        });
        threadOut.start();

        threadIn = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (in.hasNext()) {
                        String w = in.nextLine();
                        System.out.println(w);
                        if (w.contains("end")) break;
                    }
                }
                close(socket);
            }
        });
        threadIn.start();
    }

    private void sendMsg(String w) {
        out.println(w);
        out.flush();
    }

    private void close(Socket socket) {
        threadIn.interrupt();
        threadOut.interrupt();
        try {
            socket.close();
        } catch (Exception e) {
        }

    }
}
