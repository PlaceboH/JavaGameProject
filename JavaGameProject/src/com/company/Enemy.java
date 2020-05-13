package com.company;

import java.awt.*;

class Enemy implements Entity {

    protected boolean stayRight = false;
    protected boolean detected = false;
    protected double fallingSpeed;
    protected double dx,dy;
    protected boolean onGround, life;
    protected int health;

    protected Entity.FloatRect rect;
    protected TileMap tileMap;

    Enemy(TileMap tMap, int x, int y, int w, int h){
        rect = new FloatRect(x, y, w, h);
        life = true;
        tileMap = tMap;
    }

    @Override
    public void update() {

        if(life) {
            rect.left += dx;
            Collision(0);
            if (!onGround) {
                dy += fallingSpeed;
            }
            if(dx > 0){
                stayRight = true;
            }
            else {
                stayRight = false;
            }
        }
        rect.top += dy;
        onGround = false;
        Collision(1);
    }

    public boolean remove(){
        if(health > 0){
            return false;
        }
        else { return  true; }
    }

    @Override
    public void Collision(int dir) {
        int sizeOfTile = tileMap.getTileSize();
        for (int i = (int)rect.top / sizeOfTile; i < (rect.top + rect.height) / sizeOfTile; i++) {
            for (int j = (int)rect.left / sizeOfTile; j < (rect.left + rect.width) / sizeOfTile; j++) {
                if (tileMap.map[i][j] == '1'){
                    if ((dx > 0) && (dir == 0)) rect.left = j * sizeOfTile - rect.width;
                    if ((dx < 0) && (dir == 0)) rect.left = j * sizeOfTile + rect.width;
                    if ((dy > 0) && (dir == 1)) { rect.top = i * sizeOfTile - rect.height ; dy = 0; onGround = true; }
                    if ((dy < 0) && (dir == 1)) { rect.top = i * sizeOfTile + sizeOfTile; dy = 0; }

                    if(!detected) dx *= -1;
                }

            }
        }
    }

    public void bulletColl(BulletPlayer bullet) {
        if (bullet.getX() > rect.left && bullet.getX() < rect.left + 32 && bullet.getY() < rect.top + 32 && bullet.getY() > rect.top) {
            health -= bullet.getDamage();
            if(dx > 0) dx -= 0.02;
            else dx += 0.02;
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.fillRect( (int)rect.left - tileMap.getX() ,(int)rect.top, (int)rect.width, (int)rect.height);
    }

}
