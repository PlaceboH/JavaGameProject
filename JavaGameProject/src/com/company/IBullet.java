package com.company;

public interface IBullet {
    double getX();
    double getY();
    int getDamage();
    void update();
    boolean remove();
}