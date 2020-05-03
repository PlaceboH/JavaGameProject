package com.company;
import java.awt.*;

class Enemy implements Entity {

    protected boolean isFiring = false;
    protected boolean stayRight = false;
    protected boolean stayLeft = false;
    protected boolean see = false;
    protected FloatRect rect;
    protected double fallingSpeed;
    protected double dx,dy;
    protected boolean onGround, life;
    protected int health;
    protected TileMap tileMap;


    Enemy(TileMap tMap, int x, int y, int w, int h){
        rect = new FloatRect(x, y, w, h);
        life = true;
        tileMap = tMap;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.fillRect( (int)rect.left - tileMap.getX() ,(int)rect.top, (int)rect.width, (int)rect.height);
    }

    @Override
    public void update() {

        if(life == true) {

            rect.left += dx;
            Collision(0);
            if (!onGround) {
                dy += fallingSpeed;
            }
        }
        rect.top += dy;
        onGround = false;
        Collision(1);
    }

    @Override
    public void Collision(int dir) {
        int sizeOfTile = tileMap.getTileSize();
        for (int i = (int)rect.top / sizeOfTile; i < (rect.top + rect.height) / sizeOfTile; i++) {
            for (int j = (int)rect.left / sizeOfTile; j < (rect.left + rect.width) / sizeOfTile; j++) {
                if (tileMap.map[i][j] == '1') {
                    if ((dx > 0) && (dir == 0)) rect.left = j * sizeOfTile - rect.width;
                    if ((dx < 0) && (dir == 0)) rect.left = j * sizeOfTile + rect.width;
                    if ((dy > 0) && (dir == 1)) { rect.top = i * sizeOfTile - rect.height ; dy = 0; onGround = true; }
                    if ((dy < 0) && (dir == 1)) { rect.top = i * sizeOfTile + sizeOfTile; dy = 0; }
                    if(!see) dx *= -1;
                }
            }
        }
    }

    @Override
    public void bulletColl() {

    }

}
