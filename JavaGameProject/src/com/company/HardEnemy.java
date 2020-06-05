package com.company;

/**
 * Klasa HardEnemy opisuje drugi rodzaj wrogów.
 * Dziedziczy od klasy Enemy
 */
public class HardEnemy extends Enemy {

    /**
     *  Konstruktor nadaje prędkość, zdrowie, położenie, rozmiar i firing delay dla obiektów HardEnemy
     * @param tMap - TailMap
     * @param x - położenie wg osi x
     * @param y - położenie wg osi y
     */
    HardEnemy(TileMap tMap, int x, int y) {
        super(tMap, x, y, 32, 64);
        dx = -1.75;
        dy = 0;
        fallingSpeed = 3;
        health = 200;
        name = TYPE.HARD;
    }


    /**
     *  Metoda update jest odpowiedzialna za logikę obiektów HardEnemy, poruszanie się, atakowanie.
     */
    @Override
    public void update( double px, double py, int pHealth){
        super.update(px, py, pHealth);

        if(pHealth > 0) {
            double ex = rect.left;
            double ey = rect.top;
            double distX = ex - px;
            double distY = ey - py;
            int absX = Math.abs((int) distX);
            int absY = Math.abs((int) distY);

            if (absX < (GameP.WIDTH/2) + 32 && absY < 32) {
                detected = true;
                if (px > ex) {
                    dx = +2;
                } else if (px < ex) {
                    dx = -2;
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
