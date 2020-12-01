package ru.geekbrains;

public class Human implements RunAndJump {
    private int maxRun;
    private int maxJump;
    private String name;

    public Human(String name, int maxRun, int maxJump) {
        this.name = name;
        this.maxRun = maxRun;
        this.maxJump = maxJump;
    }

    public boolean run(int lenght) {
        boolean done = lenght <= maxRun;
        if (done) {
            System.out.println("Человек " + name + " успешно пробежал " + lenght + " метров");
        }
        if (!done) {
            System.out.println("Человек " + name + " пробежал только " + maxRun + " метров и устал");
        }
        return done;
    }

    public boolean jump(int height) {
        boolean done = height <= maxJump;
        if (done) {
            System.out.println("Человек " + name + " перепрыгнул препятствие " + height + " метров в высоту");
        }
        if (!done) {
            System.out.println("Человек " + name + " не смог перепрыгнуть " + height + " метров и сошел с дистанции");
        }
        return done;
    }

}


