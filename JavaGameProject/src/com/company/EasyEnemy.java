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


    /**
     *  Konstruktor nadaje prędkość, zdrowie, położenie, rozmiar i firing delay dla obiektów EasyEnemy
     * @param tMap - tailMap
     * @param x - położenie wg osi ox
     * @param y - położenie wg osi oy
     */
    EasyEnemy(TileMap tMap, int x, int y){
        super(tMap, x , y, 32, 32);
        dx = 1.5;
        dy = 0;
        fallingSpeed = 4;
        health = 100;
        firingTimer = System.nanoTime();
        firingDelay = 300;

    }


    /**
     *  Metoda update jest odpowiedzialna za logikę obiektów EasyEnemy, poruszanie się, atakowanie.
     */
    @Override
    public void update() {
        super.update();

        if(GameP.player.health > 0) {
            double px = GameP.player.getX();
            double py = GameP.player.getY();
            double ex = rect.left;
            double ey = rect.top;
            double distX = ex - px;
            double distY = ey - py;
            int absX = Math.abs((int) distX);
            int absY = Math.abs((int) distY);
            double dist = Math.sqrt(distX * distX + distY * distY);

            if ((int) dist <= 32) {
                GameP.player.hit();
            }
            if (absX < 170 && absY < 20) {
                detected = true;
                if (px > ex) {
                    dx = +1.2;
                } else if (px < ex) {
                    dx = -1.2;
                }
                long elapsed = (System.nanoTime() - firingTimer) / 1000000;
                if (elapsed > firingDelay) {
                    GameP.enemyBullets.add(new BulletEnemy((int) rect.left, (int) rect.top, stayRight));
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
