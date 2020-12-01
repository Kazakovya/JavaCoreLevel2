package ru.geekbrains;

public class Road implements Barriers{
    private int lenght;

    public Road(int lenght){
        this.lenght = lenght;
    }

    @Override
    public Boolean doAction(RunAndJump obj) {
        return obj.run(lenght);
    }
}
