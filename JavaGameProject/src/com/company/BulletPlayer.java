package com.company;

import java.awt.*;

public class BulletPlayer extends Bullet {

    @Override
    public int getDamage(){ return damage; }

    public double getX(){ return  x; }
    public double getY(){ return  y; }

    BulletPlayer(boolean changeBullets){
        x = GameP.player.getX() + 16;
        y = GameP.player.getY() + 13;

        boolean stayRight = GameP.player.getStayRight();
        boolean stayLeft = GameP.player.getStayLeft();
        boolean lookUp = GameP.player.getLookUp();

        speedY = 0;
        speedX = 0;

        if (!changeBullets) {
            color = Color.green;
            damage = 20;
            h = 2;
            w = 4;
        }
        else if (changeBullets) {
            color = Color.red;
            damage = 35;
            h = 4;
            w = 6;
        }

        if( stayRight && !lookUp ){
            speedX = 10;
            speedY = 0;
        }
        else if (stayLeft && !lookUp) {
            speedX= -10;
            speedY = 0;
        }
        else if (lookUp && GameP.player.getRun() ){
            if(stayLeft) speedX= -10;
            if(stayRight) speedX = 10;
            speedY = -10;
        }
        else if( lookUp && !GameP.player.getRun()){
            speedY = -10;
        }
    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect((int)x -  GameP.tileMap.getX(), (int)y - GameP.tileMap.getY() , w, h);
    }


}
