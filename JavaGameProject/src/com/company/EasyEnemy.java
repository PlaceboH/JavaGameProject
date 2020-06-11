package com.company;


/**
 * Klasa EasyEnemy opisuje pierwszy rodzaj wrogów
 * Dziedziczy od klasy Enemy
 */
public class EasyEnemy extends Enemy {

    /**
     * firingTimer - timer dla strzelby
     * firingDelay - wyznacza ile pocisków na sekundę maksymalnie może wystrzelić EasyEnemy
     */
    private long firingTimer, firingDelay;
    double slowSpeed_dx;

    /**
     *  Konstruktor nadaje prędkość, zdrowie, położenie, rozmiar i firing delay dla obiektów EasyEnemy
     * @param tMap - tailMap
     * @param x - położenie wg osi ox
     * @param y - położenie wg osi oy
     */
    EasyEnemy(TileMap tMap, int x, int y){
        super(tMap, x , y, 32, 32);
        position.dx = -1.5;
        position.dy = 0;
        fallingSpeed = 4;
        health = 100;
        firingTimer = System.nanoTime();
        firingDelay = 300;
        name = TYPE.EASY;
        slowSpeed_dx = 1.2;

    }



    /**
     *  Metoda update jest odpowiedzialna za logikę obiektów EasyEnemy, poruszanie się, atakowanie.
     */
    @Override
    public void update( double px, double py, int pHealth) {
        super.update(px ,py, pHealth);
        if(pHealth > 0) {
            double ex = rect.left;
            double ey = rect.top;
            double distX = ex - px;
            double distY = ey - py;
            int absX = Math.abs((int) distX);
            int absY = Math.abs((int) distY);

            int tileSize = tileMap.getTileSize();
            if (absX < tileSize*6 && absY < tileSize) {
                detected = true;
                if (px > ex) {
                    position.dx = slowSpeed_dx;
                } else if (px < ex) {
                    position.dx = -slowSpeed_dx;
                }
                long elapsed = (System.nanoTime() - firingTimer) / 1000000;
                if (elapsed > firingDelay) {
                    PlayState.addEnemyBullet(tileMap, rect.left + rect.width/4, rect.top + rect.height/4, position.stayRight);
                    firingTimer = System.nanoTime();
                }
            }
            else {
                detected = false;
            }
        }
        else{
            detected = false;
        }

    }
}
