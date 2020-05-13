package com.company;

import java.awt.*;

public class Bullet implements IBullet {

    protected int speedX;
    protected int speedY;
    protected int damage;
    protected Color color;
    protected double x;
    protected double y;
    protected int h;
    protected int w;

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void update(){
        x += speedX;
        y += speedY;
    }

    @Override
    public boolean remove(){
        for (int i = (int)y / GameP.tileMap.getTileSize(); i < (y + h) / GameP.tileMap.getTileSize(); i++) {
            for (int j = (int)x / GameP.tileMap.getTileSize(); j < (x + w) / GameP.tileMap.getTileSize(); j++) {
                if(GameP.tileMap.map[i][j] == '1' || GameP.tileMap.map[i][j] == '0'){
                    return true;
                }
            }
        }
        return false;
    }

}
