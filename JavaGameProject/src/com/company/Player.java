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
    private boolean life = true;
    private boolean isRun;


    protected FloatRect rect;
    protected int score, health;
    private double dx,dy;
    private boolean onGround;
    private long firingTimer;
    private long firingDelay;

    private long timer_s;   // start time
    private long timer_f;  // finish time
    private long timer_d; // timer duration


    private TileMap tileMap;


    public void setX(int x){ rect.left = x; }
    public void setY(int y){ rect.top = y; }
    public float getX(){ return rect.left; }
    public float getY(){ return rect.top; }

    public void setStayRight(boolean b){ stayRight = b; }
    public void setStayLeft(boolean b){ stayLeft = b; }
    public void setLookUp(boolean b){ isLookUp = b; }
    public void setJump(boolean b){ isJump = b; }
    public void setFiring(boolean b){ isFiring = b;}
    public void setRun(boolean b){ isRun = b; }
    public boolean getRun(){ return isRun; }

    public boolean getLife(){ return life; }
    public boolean getStayRight(){ return stayRight; }
    public boolean getStayLeft(){ return stayLeft; }
    public boolean getIsLay(){ return isLay; }
    public boolean getLookUp(){ return isLookUp; }
    public boolean getFiring(){ return isFiring; }
    public boolean getChangeWeapon(){ return changeWeapon; }

    public Player(TileMap tMap){
        rect = new FloatRect(100, 200, 32, 32);
        firingTimer = System.nanoTime();
        firingDelay = 150;
        tileMap = tMap;
        score = 0;
        health = 100;
        dx = 0;
        dy = 0;
        timer_s = 0;
        timer_f = 0;
        timer_d = 1000;

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.blue);
        g.fillRect( (int)rect.left - tileMap.getX() ,(int)rect.top, (int)rect.width, (int)rect.height);

        g.setColor(Color.CYAN);
        g.drawString("Health: " + health, 30 , 40 );

    }


    // Timer shock
    public boolean timerShock(){
        if(timer_s == 0){
            timer_s = System.currentTimeMillis();
            timer_f = timer_s + timer_d;
        }
        if(timer_f <= System.currentTimeMillis()){
            timer_s = 0;
            return true;
        }
        return false;
    }


    public void hit(){
        if(timerShock()){
            health -= 15;
            // some Sound
            timerShock();
        }
        isJump = true;
        dy = -5;
        isJump = false;

        System.out.println(health);
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

            if(health <= 0) life = false;

            if(stayRight && isRun){
                dx += moveSpeed;
                if(dx >= maxSpeed) {
                    dx = maxSpeed;
                }
            }
            else if(stayLeft && isRun){
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
                long elapsed = (System.nanoTime() - firingTimer)/1000000;
                if(elapsed > firingDelay) {
                    GameP.bullets.add(new Bullet(false));
                    firingTimer = System.nanoTime();
                }
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

        if(rect.left > GameP.WIDTH/2 && rect.left < tileMap.getTileSize()*tileMap.getMapWidth() - GameP.WIDTH/2) {
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
    public void bulletColl(Bullet bullet) {
        if (bullet.getX() > rect.left && bullet.getX() < rect.left + 32 && bullet.getY() < rect.top + 32 && bullet.getY() > rect.top) {
            hit();
        }
    }
}