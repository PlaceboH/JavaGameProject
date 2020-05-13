package com.company;

import java.awt.*;

public class BulletEnemy extends Bullet {

    BulletEnemy(int ex, int ey, boolean stayRight){
        x = ex + 16;
        y = ey + 13;

        speedY = 0;
        color = Color.black;
        damage = 10;
        h = 2;
        w = 4;

        if(stayRight){
            speedX = 7;
        }
        else {
            speedX= -7;
        }
    }

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

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect((int)x -  GameP.tileMap.getX(), (int)y - GameP.tileMap.getY() , w, h);
    }
}
