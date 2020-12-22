package ru.geekbrains;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    AuthService authService; // чтобы обрабатывал авторизацию
    Server server;
    Socket socket;
    DataOutputStream dataOutputStream; // выносим в состояни, чтобы был доступен везде
    DataInputStream dataInputStream;  // выносим в состояни, чтобы был доступен везде
    Client client; // сюда выносим, чтобы иметь доступ к клиенту и знать с кем работаем

    ClientHandler(AuthService authService, Server server, Socket socket) {
        this.authService = authService;
        this.server = server;
        this.socket = socket;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream()); // просим пользователя авторизоваться
            dataInputStream = new DataInputStream(socket.getInputStream());
            if (!auth(dataInputStream, dataOutputStream)) {// Авторизация. Действие №1. наша функция со своими параметрами.
                // Удаляемся из сервера
                dataInputStream.close(); // закрываем задействованные ресурсы
                dataOutputStream.close(); // закрываем задействованные ресурсы
                socket.close(); // закрываем задействованные ресурсы
                server.onClientDisconnected(this); // удаляем клиента из сервера, если вышла ошибка IOException с помощью метода onClientDisconnected из классе server
                return; // если не авторизовались выходим
            }
            server.onNewClient(this); // применяем только когда авторизовалиь, чтобы не замусоривать эфир
            messageListener(dataInputStream); // Действие 2. Прослушиваем соккет на предмет новых сообщений которые присылает клиент
        } catch (IOException e) {
            // Удаляемся из сервера
            try {
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            server.onClientDisconnected(this); // удаляем клиента из сервера, если вышла ошибка IOException с помощью метода onClientDisconnected из классе server
            e.printStackTrace();
        }
    }

    void sendMessage(Client client, String text) { // публичный, невозвратный метод для послания сообщений
        try {
            dataOutputStream.writeUTF(client.name + ": " + text);// в dataOutputStream записываем сообщение: имя + текст
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean auth(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException { // функция не возвратная и принимает на вход 2 параметра, так же выносим IOException сигнатуру
        dataOutputStream.writeUTF("Пожалуйста, введите логин и пароль через пробел!");
        //Цикл ожидания авторизации клиентов
        int tryCount = 0;
        int maxTryCount = 5; // колличество попыток авторизации
        while (true) { // слушаем чат на предмет данных автоизации(логин, пароль)
            // Читаем команду от клиента
            // /auth petya 1234
            String newMessage = dataInputStream.readUTF(); // новое сообщение
            // Разбиваем сообщения на составляющие команды
            String[] messageData = newMessage.split("\\s"); // разбивка нового сообщения по пробелам
            // Проверяем соответствует ли команда команде авторизации
            if (messageData.length == 3 && messageData[0].equals("/auth")) {// если первый элемент сообщения начинается со /auth, так же должно быть передано 3 параметра
                tryCount++; // инкремент, увеличиваем на 1
                String login = messageData[1];// логин индекс 1
                String password = messageData[2]; // пароль индекс 2
                // Зарегистрирован ли данный пользователь
                client = authService.auth(login, password); // сохраняем нашего клиента
                if (client != null) { // проверяем если клиент не равен null, т.е если клиент нашёлся
                    // Если получилось авторизоваться, то выходим из цикла
                    break;
                } else {
                    dataOutputStream.writeUTF("Неправильные логин и пароль!");
                }
            } else {// если нет /auth, то сообщение или передано не 3 параметра
                dataOutputStream.writeUTF("Ошибка авторизации!");
            }
            if (tryCount == maxTryCount) { // исчерпано колличество попыток
                dataOutputStream.writeUTF("Превышен лимит попыток!");
                dataInputStream.close(); // нужно закрывать, чтобы не крутились в безконечном цикле
                dataOutputStream.close();
                socket.close(); // закрываем соккет, прекращаем общение
                return false; // выход (вся функция или конструктор не будет больше исполняться)
            }
        }
        return true;
    }

    private void messageListener(DataInputStream dataInputStream) throws IOException { // метод обрабатывает сообщения, и 2 параметра на вход
        while (true) {
            String newMessage = dataInputStream.readUTF();// сообщение получаем из dataInputStream
            if (newMessage.equals("/exit")) { // если появляется слово /exit, то не отсылаем его как сообщение
                dataInputStream.close();
                dataOutputStream.close();
                socket.close(); // закрываем соккет, так как это задействованный ресурс

            } else if (newMessage.startsWith("/w")) { // если сообщение будет начинаться с /w, то
                String messageWithoutCommand = newMessage.substring(3);
                int messageIndex = messageWithoutCommand.indexOf(" "); // задаём откуда начинается след пробел
                String nick = messageWithoutCommand.substring(0, messageIndex); // получаем ник
                String message = messageWithoutCommand.substring(messageIndex); // пилим строку и отделяем ник от сообщения. Из всего текста вырезаем от начала сообщения до его размера
                server.sendMessageTo(client, nick, message); // передаём клиента, получателя и сообщение
            } else {
                server.sendBroadCastMessage(client, newMessage); // регистрируем клиента и сообщение на сервере
            }
        }
    }
}

