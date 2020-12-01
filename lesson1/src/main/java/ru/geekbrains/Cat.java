package ru.geekbrains;

public class Cat implements RunAndJump {

    private int maxRun;
    private int maxJump;
    private String name;

    public Cat(String name, int maxRun, int maxJump) {
        this.name = name;
        this.maxRun = maxRun;
        this.maxJump = maxJump;
    }

    @Override
    public boolean run(int lenght) {
        boolean done = lenght <= maxRun;
        if (done) {
            System.out.println("Кот " + name + " успешно пробежал " + lenght + " метров");
        }
        if (!done) {
            System.out.println("Кот " + name + " пробежал только " + maxRun + " метров и лег спать");
        }
        return done;
    }

    public boolean jump(int height) {
        boolean done = height <= maxJump;
        if (done) {
            System.out.println("Кот " + name + " перепрыгнул препятствие " + height + " метров");
        }
        if (!done) {
            System.out.println("Кот " + name + " не смог перепрыгнуть " + height + " метров и лег спать");
        }
        return done;
    }

}