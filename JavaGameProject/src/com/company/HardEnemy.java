package com.company;

/**
 * Klasa HardEnemy opisuje drugi rodzaj wrogów.
 * Dziedziczy od klasy Enemy
 */
public class HardEnemy extends Enemy {

    /**
     *  Konstruktor nadaje prędkość, zdrowie, położenie, rozmiar i firing delay dla obiektów HardEnemy
     * @param tMap
     * @param x
     * @param y
     */
    HardEnemy(TileMap tMap, int x, int y, int w, int h) {
        super(tMap, x, y, w, h);
        dx = 1;
        dy = 0;
        fallingSpeed = 4;
        health = 200;

    }

    /**
     *  Metoda update jest odpowiedzialna za logikę obiektów HardEnemy, poruszanie się, atakowanie.
     */
    @Override
    public void update(){
    }

}
