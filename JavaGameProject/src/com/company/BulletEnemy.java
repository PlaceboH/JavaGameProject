package com.company;

/**
 * Klasa BulletEnemy jest odpowiedzialna za pociski dla wrogów
 */
public class BulletEnemy extends Bullet {

    /**
     * Konstruktor zadaje położenie, prędkość, rozmiar, kolor, damage pocisku
     * @param ex - położenie pocisku oś x
     * @param ey - położenie pocisku oś y
     * @param stayRight - czy wróg powrócony w prawą stronę
     */
    BulletEnemy(TileMap tileMap, double ex, double ey, boolean stayRight){
        super(tileMap);
        rect = new Coordinate<Double>(ex + 16, ey + 13, 3, 3);
        speedY = 0;
        damage = 10;

        if(stayRight){
            speedX = 7;
        }
        else {
            speedX= -7;
        }
    }

}
