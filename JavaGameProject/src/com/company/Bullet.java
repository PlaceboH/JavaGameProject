package com.company;

import java.awt.*;

public class Bullet {
    private double x;
    private double y;
    private int h;
    private int w;
    private int speed;
    private Color color;
    private TileMap tMap;
    Bullet(){
        x = GameP.player.getX();
        y = GameP.player.getY();
        h = 2;
        w = 4;
        speed = 10;
        color = Color.green;
    }
    public void update(){
        x -= speed;
    }
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
    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect((int)x -  GameP.tileMap.getX(), (int)y, w, h);
    }

}
