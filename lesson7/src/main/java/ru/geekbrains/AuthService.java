package ru.geekbrains;

import java.util.ArrayList;
import java.util.List;

class AuthService { // создали сервис авторизации
    List<Client> clients = new ArrayList();

    AuthService() { // добавляем клиентов и информацию о них
        clients.add(new Client("Pavel", "pavel1", "1234"));
        clients.add(new Client("Oleg", "oleg1", "1234"));
        clients.add(new Client("Nick", "nick1", "4321"));
    }

    synchronized Client auth(String login, String password) { // проверка авторизации. synchronized потомучто к нему обращаются разные потоки
        for (int i = 0; i < clients.size(); i++) { // прошли всех клиентов
            Client client = clients.get(i); // вызываем клиента по индексу
            if (client.login.equals(login) && client.password.equals(password)) {
                return client; // возвращает если клиент есть
            }
        }
        return null; // не нашли
    }
}
