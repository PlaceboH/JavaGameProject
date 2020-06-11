package com.company;

/**
 * Klasa Player opisuje bohatera gry
 */
public class Player implements Hero {

    /**
     * Czy bohater strzela
     */
    private boolean isFiring = false;

    /**
     * Czy bohater 'patrzy' w górę
     */
    private boolean isLookUp = false;

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

    private Position position;

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
    private int health;

    private static int items = 0;


    private TileMap tMap;

    public int getItems(){ return items; }
    public void setX(int x){ rect.left = x; }
    public void setY(int y){ rect.top = y; }
    public float getX(){ return rect.left; }
    public float getY(){ return rect.top; }
    public float getH(){ return rect.height; }
    public float getW(){ return rect.width; }
    public int getHealth(){ return health; }
    public boolean getLife(){ return life; }
    public void setStayRight(boolean b){ position.stayRight = b; }
    public boolean getStayRight() { return position.stayRight; }
    public boolean getStayLeft() { return position.stayLeft; }
    public void setStayLeft(boolean b){ position.stayLeft = b; }
    public boolean getLookUp(){ return isLookUp; }
    public void setLookUp(boolean b){ isLookUp = b; }
    public void setJump(boolean b){ isJump = b; }
    public void setFiring(boolean b){ isFiring = b;}
    public boolean getRun(){ return isRun; }
    public void setRun(boolean b){ isRun = b; }


    /**
     *  Konstruktor
     *  Inicjalizacja bohatera
     */
    public Player(TileMap tileMap){
        rect = new FloatRect(100, 200, 32, 32);
        position = new Position(0, 0, false, false, true);
        firingTimer = System.nanoTime();
        hitTimer = System.nanoTime();
        firingDelay = 220;
        hitDelay = 1000;
        health = 100;
        tMap = tileMap;
    }


    /**
     *  Uderzenie bohatera
     *  Metoda odejmuje zdrowie bohaterowi nie częściej niż raz w 1 sekundę
     */
    @Override
    public void hit(){
        long elapsed = (System.nanoTime() - hitTimer)/1000000;
        if(elapsed > hitDelay && life) {
            health -= 15;
            position.dy = -5;
            hitTimer = System.nanoTime();
        }
    }


    /**
     *  Akcja bohatera (Poruszanie się, strzelba)
     */
    @Override
    public void update() {
        double fallingSpeed = 0.5;
        double maxFallingSpeed = 8;
        double jumpSpeed = -10;

        if(life){
            checkIsLife();
            updateMoving();
            if(isJump && position.onGround){
                position.dy = jumpSpeed;
                position.onGround = false;
                isJump = false;
            }
            updateFiring();
            rect.left += position.dx;
        }

        Collision(0);
        if (!position.onGround) {
            position.dy += fallingSpeed;
            if(position.dy >= maxFallingSpeed){
                position.dy = maxFallingSpeed;
            }
        }
        rect.top += position.dy;
        position.onGround = false;
        Collision(1);
    }

    private void checkIsLife(){
        if(health <= 0){
            life = false;
            health = 0;
        }
    }

    private void updateFiring(){
        if(isFiring){
            long elapsed = (System.nanoTime() - firingTimer)/1000000;
            if(elapsed > firingDelay) {
                double middleX = rect.left + rect.width/2;
                double middleY = rect.top + rect.height/2;
                PlayState.addPlayerBullet(tMap, changeWeapon, middleX, middleY );
                firingTimer = System.nanoTime();
            }
        }
    }

    private void updateMoving(){
        double moveSpeed = 0.5;
        double maxSpeed = 3;
        double stopSpeed = 1;
        if(position.stayRight && isRun){
            position.dx += moveSpeed;
            if(position.dx >= maxSpeed) {
                position.dx = maxSpeed;
            }
        }
        else if(position.stayLeft && isRun){
            position.dx -= moveSpeed;
            if(position.dx <= -maxSpeed){
                position.dx = -maxSpeed;
            }
        }
        else {
            if(position.dx > 0){
                position.dx -= stopSpeed;
                if(position.dx < 0){
                    position.dx = 0;
                }
            }
            else if(position.dx < 0){
                position.dx += stopSpeed;
                if(position.dx > 0){
                    position.dx = 0;
                }
            }
        }
    }

    /**
     * Zetknięcie się bohatera z przeszkodami na mapie
     * @param dir - oś ox dir = 0  lub oy dla dir = 1
     */
    @Override
    public void Collision(int dir) {
        int sizeOfTile = tMap.getTileSize();
        for (int i = (int)rect.top / sizeOfTile; i < (rect.top + rect.height) / sizeOfTile; i++) {
            for (int j = (int)rect.left / sizeOfTile; j < (rect.left + rect.width) / sizeOfTile; j++) {
                if (tMap.map[i][j] == 'B' || tMap.map[i][j] == 'K' || tMap.map[i][j] == 'L' || tMap.map[i][j] == 'W' ) {

                    if ((position.dx > 0) && (dir == 0)) rect.left = j * sizeOfTile - rect.width;
                    if ((position.dx < 0) && (dir == 0)) rect.left = j * sizeOfTile + rect.width;
                    if ((position.dy > 0) && (dir == 1)) { rect.top = i * sizeOfTile - rect.height ; position.dy = 0; position.onGround = true; }
                    if ((position.dy < 0) && (dir == 1)) { rect.top = i * sizeOfTile + sizeOfTile; position.dy = 0; }
                }
                if(tMap.map[i][j] == 't') hit();

                if(tMap.map[i][j] == 'g'){
                    tMap.map[i][j] = ' ';
                    changeWeapon = true;
                }
                if(tMap.map[i][j] == 's'){
                    tMap.map[i][j] = ' ';
                    items++;
                }
            }
        }
    }


    /**
     * Metoda Sprawdza czy pocisk wrogów trafił w bohatera
     * @param bullet - pocisk wroga
     */
    @Override
    public void bulletColl(BulletEnemy bullet) {
        int x  = (int)((double)bullet.rect.x);
        int y = (int)((double)bullet.rect.y);
        if (x > rect.left && x < rect.left + 32 && y < rect.top + 32 && y > rect.top) {
            hit();
        }
    }

}