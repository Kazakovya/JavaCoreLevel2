package ru.geekbrains;

public class Main {
    public static void main(String[] args) {

        RunAndJump[] runners = new RunAndJump[3];
        runners[0] = new Cat("Кот", 500, 3);
        runners[1] = new Human("Человек", 2000, 2);
        runners[2] = new Robot("Робот", 50000, 5);

        Barriers[] barriers = new Barriers[3];
        barriers[0] = new Road(200);
        barriers[1] = new Wall(3);
        barriers[2] = new Road(5000);


        for (RunAndJump runner : runners) {
            for (Barriers barrier : barriers) {
                if (!barrier.doAction(runner)) {
                    break;
                }
            }
        }
    }

}
