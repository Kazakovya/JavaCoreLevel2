package ru.geekbrains;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    List<ClientHandler> clients = new ArrayList<>(); // создаём список ClientHandler-ов
    Map<String, List<Message>> chats = new HashMap<>();//Map<От строки к списку сообщений>, чтобы сообщения хранились индивидуально для каждой пары клиентов

    Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            AuthService authService = new AuthService();
            // обработчик клиентов
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> { //Действие 4. Создаем многопоточность, чтобы чат не падал и подключалось много клиентов каждый в отдельном потоке
                    ClientHandler clientHandler = new ClientHandler(authService, this, socket); // нужно, чтобы сохранять информацию о клиенте. Список подключенных на данный момент пользователей
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Сервер прекратил работу с ошибкой");
            e.printStackTrace();
        }
    }

    synchronized void sendBroadCastMessage(Client sender, String text) { // обработчик сообщений, синхронизирован чтобы обращались к нему из разных потоков. 2 параметра клиент и сообщение
        for (int i = 0; i < clients.size(); i++) {
            String recipientLogin = clients.get(i).client.login; // пербираем клиентов, берём логин
            sendMessageTo(sender, recipientLogin, text); // выводим информацию
        }
    }

    synchronized void sendMessageTo(Client sender, String recepientLogin, String text) { //(sender - от кого, recepientLogin - кому, text - текст сообщения)
        String senderLogin = sender.login; // получаем логин отправителя
        String key; // создали переменную ключ
        if (senderLogin.compareTo(recepientLogin) > 0) { // сравниваем их
            key = senderLogin + recepientLogin;
        } else {
            key = recepientLogin + senderLogin;
        }
        // есть ли такой чат
        if (!chats.containsKey(key)) { // если messages не содержит такой ключ
            chats.put(key, new ArrayList()); // тогда в messages по этому ключу создадим пустой список
        }
        // сохраняем сообщения
        chats.get(key).add(new Message(sender, text)); // тогда мы сможем получить этот список по ключу и добавить в него сообщение. sender это клиент
        // ищем получателя среди клиентов
        ClientHandler recipient = null;
        for (int i = 0; i < clients.size(); i++) { // бежим по списку
            ClientHandler client = clients.get(i); // получаем клиента по индексу
            if (client.client.login.equals(recepientLogin)) { // сравниваем равен ли логин этого клиента нашему получателю
                recipient = client; // если равен, то его записываем
            }
        }
        // Если получатель онлайн то отправляем ему сообщение
        if (recipient != null) { // если нашли нашего получателя
            recipient.sendMessage(sender, text); // тогда отправляем ему сообщение от нашего клиента с каким-то текстом
            System.out.println("Отправлено сообщение для " + recepientLogin);
        } else {
            System.out.println("Получатель не найден");
        }
    }

    synchronized void onNewClient(ClientHandler clientHandler) { // будет принимать в себе ClientHandler. Публичный
        clients.add(clientHandler); // добавляем сообщение с клиентом и текстом в список List<ClientHandler> clients
        // for (int i = 0; i < messages.size(); i++) { // при подключении нового клиента, он получит всю историю
        // Message message = messages.get(i); // сообщение по индексу
        // clientHandler.sendMessage(message.client, message.text); // при подключении нового клиента, он получит всю историю
        //  }
        sendBroadCastMessage(clientHandler.client, "вошёл в чат");
    }

    synchronized void onClientDisconnected(ClientHandler clientHandler) { // Вызывать этот метод будет сам ClientHandler в конструкторе в классе ClientHandler во всех случаях ошибки IOException
        clients.remove(clientHandler); // удаляем клиентов из списка
        sendBroadCastMessage(clientHandler.client, "Покинул чат");
    }

    public static void main(String[] args) { // код пишем отдельно, так как main статичная функция и в ней не хранится никакого состояния
        new Server(); // запук сервера
    }
}
