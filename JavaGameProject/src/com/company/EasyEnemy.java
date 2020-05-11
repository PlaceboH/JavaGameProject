package com.company;

public class EasyEnemy extends Enemy {
    private boolean isFiring = false;


    public boolean getFiring(){ return isFiring; }
    public void setFiring( boolean b) { isFiring = b; }


    EasyEnemy(TileMap tMap, int x, int y){
        super(tMap, x , y, 32, 32);
        dx = 2;
        dy = 0;
        fallingSpeed = 4;
        health = 100;
    }
}
