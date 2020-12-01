package ru.geekbrains;

public class Wall implements Barriers{
    private int height;

    public Wall(int height){
        this.height = height;
    }

    @Override
    public Boolean doAction(RunAndJump obj) {
        return obj.jump(height);
    }
}
