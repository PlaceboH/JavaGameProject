package com.company;
import java.awt.*;

/**
 * Klasa Player opisuje bohatera gry
 */
public class Player implements Entity {

    /**
     * Czy bohater strzela
     */
    private boolean isFiring = false;

    /**
     * Czy bohater 'patrzy' w prawą stronę
     */
    private boolean stayRight = false;

    /**
     * Czy bohater 'patrzy' w lewą stronę
     */
    private boolean stayLeft = false;

    /**
     * Czy bohater 'patrzy' w górę
     */
    private boolean isLookUp = false;


    //   private boolean isLay = false;
    /**
     * Czy bohater robi skok
     */
    private boolean isJump = false;
    /**
     * Czy bohater biegnie
     */
    private boolean isRun;

    /**
     * Czy bohater zmienia rodzaj pocisku
     */
    private boolean changeWeapon = false;

    /**
     * Czy bohater żyje
     */
    private boolean life = true;

    /**
     * Prędkość poruszania sie wzdóż osi ox i oy
     */
    private double dx,dy;

    /**
     * Czy bohater jest na ziemi
     */
    private boolean onGround;

    /**
     * firingTimer - timer dla strzelby
     * firingDelay - wyznacza ile pocisków na sekundę maksymalnie może wystrzelić bohater
     */
    private long firingTimer;
    private long firingDelay;

    /**
     * hitTimer - timer start
     * hitDelay - czas podczas którego bohater jest niezniszczalny
     */
    private long hitTimer;
    private long hitDelay;

    /**
     *  połorzenie (x,y) i rozmir (width, height)
     */
    private FloatRect rect;

    /**
     * health - zdrowie bohatera
     */
    protected int health;



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
    //    public boolean getIsLay(){ return isLay; }
    public boolean getLookUp(){ return isLookUp; }
    public boolean getFiring(){ return isFiring; }
    public boolean getChangeWeapon(){ return changeWeapon; }


    /**
     *  Konstruktor
     *  Inicjalizacja bohatera
     */
    public Player(){
        rect = new FloatRect(100, 200, 32, 32);
        firingTimer = System.nanoTime();
        hitTimer = System.nanoTime();
        firingDelay = 150;
        hitDelay = 1000;
        health = 100;
        dx = 0;
        dy = 0;
    }


    /**
     *  Uderzenie bohatera
     *  Metoda odejmuje zdrowie bohaterowi nie częściej niż raz w 1 sekundę
     */
    public void hit(){
        long elapsed = (System.nanoTime() - hitTimer)/1000000;
        if(elapsed > hitDelay && life) {
            health -= 15;
            dy = -5;
            hitTimer = System.nanoTime();
        }
        System.out.println(health);
    }


    /**
     *  Akcja bohatera (Poruszanie się, strzelba)
     */
    @Override
    public void update() {
        double moveSpeed = 0.5;
        double maxSpeed = 3;
        double stopSpeed = 1;
        double fallingSpeed = 0.5;
        double maxFallingSpeed = 8;
        double jumpSpeed = -10;

        if(life){

            if(health <= 0){
                life = false;
                health = 0;
            }

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
                    GameP.bullets.add(new BulletPlayer(false));
                    firingTimer = System.nanoTime();
                }
            }

            rect.left += dx;
        }
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
    }



    /**
     * Zetknięcie się bohatera z przeszkodami na mapie
     * @param dir - oś ox dir = 0  lub oy dla dir = 1
     */
    @Override
    public void Collision(int dir) {

        int sizeOfTile = GameP.tileMap.getTileSize();
        for (int i = (int)rect.top / sizeOfTile; i < (rect.top + rect.height) / sizeOfTile; i++) {
            for (int j = (int)rect.left / sizeOfTile; j < (rect.left + rect.width) / sizeOfTile; j++) {
                if (GameP.tileMap.map[i][j] == '1') {
                    if ((dx > 0) && (dir == 0)) rect.left = j * sizeOfTile - rect.width;
                    if ((dx < 0) && (dir == 0)) rect.left = j * sizeOfTile + rect.width;
                    if ((dy > 0) && (dir == 1)) { rect.top = i * sizeOfTile - rect.height ; dy = 0; onGround = true; }
                    if ((dy < 0) && (dir == 1)) { rect.top = i * sizeOfTile + sizeOfTile; dy = 0; }
                }
            }
        }
    }

    /**
     * Metoda Sprawdza czy pocisk wrogów trafił w bohatera
     * @param bullet - pocisk wroga
     */
    public void bulletColl(BulletEnemy bullet) {
        if (bullet.getX() > rect.left && bullet.getX() < rect.left + 32 && bullet.getY() < rect.top + 32 && bullet.getY() > rect.top) {
            hit();
        }
    }


    /**
     * Metoda draw wyświetla bohatera i linię zdrowia bohatera
     * @param  g - obiekt wspomagający wyświetlaniu
     */
    public void draw(Graphics2D g) {
        g.setColor(Color.blue);
        g.fillRect( (int)rect.left - GameP.tileMap.getX() ,(int)rect.top, (int)rect.width, (int)rect.height);

        if(health > 20) {
            g.setColor(Color.orange);
        }
        else {
            g.setColor(Color.red);
        }
        g.fillRect(77, 25, health, 15);
        g.drawString("Hp", 20, 50);

    }
}