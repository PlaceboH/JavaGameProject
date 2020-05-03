package com.company;

public class EasyEnemy extends Enemy {

    EasyEnemy(TileMap tMap, int x, int y){
        super(tMap, x , y, 32, 32);
        dx = 2;
        dy = 0;
        fallingSpeed = 4;
        health = 100;
    }
}
