package com.company;

/**
 * Klasa BulletEnemy jest odpowiedzialna za pociski dla bohatera
 */
public class BulletPlayer extends Bullet {

    /**
     * Konstruktor zadaje położenie, prędkość, rozmiar, kolor, damage pocisku dla gracza
     * @param changeBullets - czy trzeba zmienić rodzaj pocisków
     */
    BulletPlayer(TileMap tileMap, boolean changeBullets , double px, double py, boolean stayRight, boolean stayLeft, boolean lookUp, boolean run){
        super(tileMap);
        rect = new Coordinate<Double>(px + 16,py + 13 );
        speedY = 0;
        speedX = 0;

        if (!changeBullets) {
            damage = 20;
            rect.h = 2;
            rect.w = 4;
        }
        else if (changeBullets) {
            damage = 35;
            rect.h = 4;
            rect.w = 6;
        }

        if( stayRight && !lookUp ){
            speedX = 8;
            speedY = 0;
        }
        else if (stayLeft && !lookUp) {
            speedX= -8;
            speedY = 0;
        }
        else if (lookUp && run ){
            if(stayLeft) speedX= -8;
            if(stayRight) speedX = 8;
            speedY = -8;
        }
        else if(lookUp){
            speedY = -8;
        }
    }

}
