package com.company;

import java.awt.*;

/**
 * Klasa Bulet opisuje podstawowe właściwości pocisków
 */
public class Bullet {

    /**
     *  SpeedX - prędkość pocisku wzdłuż osi x
     *  SpeedY - prędkość pocisku wzdłuż osi y
     */
    protected int speedX, speedY;

    /**
     *  damage - zniszczalność pocisku
     */
    protected int damage;

    /**
     * color - kolor pocisku
     */
    protected Color color;

    /**
     * x,y połorzenie pocisku
     */
    protected double x, y;
    /**
     * w,h - rozmiary pocisku
     */
    protected int h, w;


    /**
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * @return damage
     */
    public int getDamage() {
        return damage;
    }


    /**
     * Metoda update zmienia połorzenie pocisków
     */
    public void update(){
        x += speedX;
        y += speedY;
    }


    /**
     *  Metoda remove
     * @return true jeżeli pocisk trafił w przeszkodę na mapie
     */
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
