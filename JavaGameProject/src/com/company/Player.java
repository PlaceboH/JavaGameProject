package com.company;
import java.awt.*;
public class Player {
    private double x;
    private double y;
    private double dx;
    private double dy;

    private double width;
    private double height;

    private boolean left;
    private boolean right;
    private boolean jump;
    private boolean fall;

    private double moveSpeed;
    private double maxSpeed;
    private double maxFallSpeed;
    private double stopSpeed;
    private double jumpStart;
    private double gravitation;

    private TileMap tileMap;

    public Player(TileMap tMap){
        tileMap = tMap;
        width = 20;
        height = 20;
        moveSpeed = 0.6;
        maxSpeed = 4.5;
        stopSpeed = 0.3;
        jumpStart = -11.0;
        gravitation = 0.64;
    }
    public void setLeft(boolean l) {
        left = l;
    }
    public void setRight(boolean r){
        right = r;
    }

    private void update(){

    }


}
