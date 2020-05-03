package com.company;
import java.awt.*;


public class Player implements Entity {

    private boolean isFiring = false;
    private boolean stayRight = false;
    private boolean stayLeft = false;
    private boolean isLookUp = false;
    private boolean isLay = false;
    private boolean changeWeapon = false;
    private boolean isJump = false;


    private FloatRect rect;
    private double dx,dy;
    private boolean onGround, life;
    private double currentFrame, jumpFrame, runUpFrame;
    private int score, health;
    private double TimeShoot;
    private double timer;
    private double timerL;
    private double BulletTimer;
    private TileMap tileMap;


    public void setX(int x){ rect.left = x; }
    public void setY(int y){ rect.top = y; }
    public float getX(){ return rect.left; }
    public float getY(){ return rect.top; }
    public void setStayRight(boolean b){ stayRight = b; }
    public void setStayLeft(boolean b){ stayLeft = b; }
    public void setIsLay(boolean b){ isLay = b; }
    public void setLookUp(boolean b){ isLookUp = b; }
    public void setFiring(boolean b){ isFiring = b; }
    public void setJump(boolean b){ isJump = b; }
    public void setChangeWeapon(boolean b){ changeWeapon = b; }

    public boolean getStayRight(){ return stayRight; }
    public boolean getStayLeft(){ return stayLeft; }
    public boolean getIsLay(){ return isLay; }
    public boolean getLookUp(){ return isLookUp; }
    public boolean getFiring(){ return isFiring; }
    public boolean getChangeWeapon(){ return changeWeapon; }

    public Player(TileMap tMap){
        rect = new FloatRect(100, 200, 32, 32);
        tileMap = tMap;
        score = 0;
        life = true;
        health = 100;
        dx = 0;
        dy = 0;
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(Color.red);
        g.fillRect( (int)rect.left - tileMap.getX() ,(int)rect.top, (int)rect.width, (int)rect.height);
    }

    @Override
    public void update() {
        double moveSpeed = 0.5;
        double maxSpeed = 3;
        double stopSpeed = 1;
        double fallingSpeed = 0.5;
        double maxFallingSpeed = 8;
        double jumpSpeed = -10;

        if(life){

            if(stayRight){
                dx += moveSpeed;
                if(dx >= maxSpeed) {
                    dx = maxSpeed;
                }
            }
            else if(stayLeft){
                dx -= moveSpeed;
                if(dx <= -maxSpeed){
                    dx = -maxSpeed;
                }
            }
            else {
                if(dx > 0){
                    dx -= stopSpeed;
                    if(dx < 0){
                        dx = 0;
                    }
                }
                else if(dx < 0){
                    dx += stopSpeed;
                    if(dx > 0){
                        dx = 0;
                    }
                }
            }

            if(isJump && onGround){
                dy = jumpSpeed;
                onGround = false;
                isJump = false;
            }

            if(isFiring){
                GameP.bullets.add(new Bullet());
            }

        }
        rect.left += dx;
        Collision(0);
        if (!onGround) {
            dy += fallingSpeed;
            if(dy >= maxFallingSpeed){
                dy = maxFallingSpeed;
            }
        }
        rect.top += dy;
        onGround = false;
        Collision(1);


        if(rect.left > GameP.WIDTH/2 && rect.left < tileMap.getTileSize()*tileMap.getMapWidth() - GameP.WIDTH/2+14) {
            tileMap.setX((int) (rect.left - GameP.WIDTH / 2));
        }

    }

    @Override
    public void Collision(int dir) {

        int sizeOfTile = tileMap.getTileSize();
        for (int i = (int)rect.top / sizeOfTile; i < (rect.top + rect.height) / sizeOfTile; i++) {
            for (int j = (int)rect.left / sizeOfTile; j < (rect.left + rect.width) / sizeOfTile; j++) {
                if (tileMap.map[i][j] == '1') {
                    if ((dx > 0) && (dir == 0))
                        rect.left = j * sizeOfTile - rect.width;
                    if ((dx < 0) && (dir == 0))
                        rect.left = j * sizeOfTile + rect.width;
                    if ((dy > 0) && (dir == 1)) {
                        rect.top = i * sizeOfTile - rect.height ;
                        dy = 0;
                        onGround = true;
                    }
                    if ((dy < 0) && (dir == 1)) {
                        rect.top = i * sizeOfTile + sizeOfTile;
                        dy = 0;
                    }
                }
            }
        }

    }

    @Override
    public void bulletColl() {

    }
}