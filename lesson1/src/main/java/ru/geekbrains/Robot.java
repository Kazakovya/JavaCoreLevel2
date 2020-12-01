package ru.geekbrains;

public class Robot implements RunAndJump {
    private int maxRun;
    private int maxJump;
    private String name;

    public Robot(String name, int maxRun, int maxJump){
        this.name = name;
        this.maxRun = maxRun;
        this.maxJump = maxJump;
    }
    public boolean run(int lenght) {
        boolean done = lenght <= maxRun;
        if (done) {
            System.out.println("Робот " + name + " успешно пробежал " + lenght + " метров");
        }
        if (!done) {
            System.out.println("Робот " + name + " пробежал только " + maxRun + " метров, и у него сели батарейки");
        }
        return done;

    }

    public boolean jump(int height) {
        boolean done = height <= maxJump;
        if (done) {
            System.out.println("Робот " + name + " перепрыгнул препятствие " + height + " метров");
        }
        if (!done) {
            System.out.println("Робот " + name + " не смог перепрыгнуть " + height + " метров и сошел с дистанции");
        }
        return done;
    }
}

