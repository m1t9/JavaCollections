package com.javarush.task.task25.task2515;

public class SpaceShip extends BaseObject{

    private double dx = 0;

    public SpaceShip(double x, double y) {
        super(x, y, 3);
    }

    public void moveLeft() {
        dx = -1;
    }

    public void moveRight() {
        dx = 1;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void move() {
        x += dx;
        this.checkBorders(2, Space.game.getWidth() - 2, 2, Space.game.getHeight() - 2);
    }

    public void fire() {
        Rocket rocketLeft = new Rocket(x - 2, y);
        Space.game.getRockets().add(rocketLeft);
        Rocket rocketRight = new Rocket(x + 2, y);
        Space.game.getRockets().add(rocketRight);
    }
}
