package com.company;

import com.company.Graphics.Graphic;
import com.company.Graphics.PlayerGraphic;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *  Klasa odpowiedzialna za stan gry
 */
public class PlayState {

    /**
     *  obiekt potrzebny do wyrysowania obiektów gry
     */
    private Graphic graphic;
    /**
     *  obiekt potrzebny do wyświetlenia gracza
     */
    private PlayerGraphic playerGraphic;
    /**
     *  obiekt odpowiadający za mapkę
     */
    private TileMap tileMap;
    /**
     *  obiekt odpowiadający za głównego bohatera(player)
     */
    private static Player player;
    /**
     *  lista obiektów wrogów
     */
    private static ArrayList<Enemy> enemies;
    /**
     *  lista obiektów pocisków gracza
     */
    private static ArrayList<BulletPlayer> bullets;
    /**
     *  lista obiektów pocisków wrogów
     */
    private static ArrayList<BulletEnemy> enemyBullets;
    /**
     *  ndeks poziomu
     */
    public static int level = 1;
    /**
     *  ilość zniszczonych wrogów
     */
    public static int killedEnemies = 0;

    PlayState(){
        initLevel();
        initObjects();
    }

    /**
     *  Metoda inicjalizuje obiekty gry oraz obiekty interfejsu
     */
    private void initObjects(){
        player = new Player(tileMap);
        player.setX(150);
        player.setY(GameP.HEIGHT - 300);
        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<BulletPlayer>();
        enemyBullets = new ArrayList<BulletEnemy>();
        graphic = new Graphic();
        playerGraphic = new PlayerGraphic(level);
    }

    /**
     *  Metoda inicjalizuje mapki gry
     */
    private void initLevel(){
        if(level == 1){
            tileMap = new TileMap("JavaGameProject/Map/map1.txt", 32);
        }
        else if(level == 2){
            tileMap = new TileMap("JavaGameProject/Map/map2.txt", 32);
        }
        else if(level == 3){
            tileMap = new TileMap("JavaGameProject/Map/map3.txt", 32);
        }
    }

    /**
     *  Metoda draw wywołuje metody draw u wszystkich obiektów gry
     */
    public void draw(Graphics2D g) {
        // draw bullets for player
        for(int i = 0; i < bullets.size(); i++){
            graphic.drawPlayerBullet(g, tileMap.getX(), tileMap.getY(), bullets.get(i).rect.x, bullets.get(i).rect.y, bullets.get(i).rect.w, bullets.get(i).rect.h);
        }
        // draw bullets for enemies
        for(int i = 0; i < enemyBullets.size(); i++){
            graphic.drawBulletEasyEnemy(g, tileMap.getX(), tileMap.getY(), enemyBullets.get(i).rect.x, enemyBullets.get(i).rect.y, enemyBullets.get(i).rect.w, enemyBullets.get(i).rect.h);
        }
        // draw enemies
        for(int i = 0; i < enemies.size(); i++){
            graphic.drawEnemy(g, tileMap.getX(), enemies.get(i).rect.left, enemies.get(i).rect.top, enemies.get(i).rect.width, enemies.get(i).rect.height, enemies.get(i).stayRight);
        }
        //draw map
        tileMap.draw(g);
        // draw player and health bar
        playerGraphic.draw(g, (int)player.getX(), (int)player.getY(), (int)player.getW(), (int)player.getH(), tileMap.getX(), player.getHealth(), player.getStayRight());
    }

    /**
     *  Metoda updateLevel wywołuje metodę update obiektu tailMap
     *  Odpowiada za zmianę poziomu
     */
    private void updateLevel(){
        if(player.getX() > (tileMap.getMapWidth()*tileMap.getTileSize()) - 100)
        {
            level++;
            initLevel();
            initObjects();
        }
        tileMap.update(player.getX());
    }

    /**
     *  Metoda update wywołuje metody update obiektów tileMap, player, bullet, enemies, enemyBullet
     */
    public void update(){
        if(level <= 3) {
            updateLevel();
        }
        player.update();
        // player bullets update and remove
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).update();
            boolean remove = bullets.get(i).remove();
            if(remove){
                bullets.remove(i);
                i--;
            }
        }
        // Enemy bullets update
        for(int i = 0; i < enemyBullets.size(); i++){
            enemyBullets.get(i).update();
            boolean remove = enemyBullets.get(i).remove();
            if(remove){
                enemyBullets.remove(i);
                i--;
            }
        }
        // Enemy update
        for(int i = 0; i < enemies.size(); i++ ){
            enemies.get(i).update(player.getX(), player.getY(), player.getHealth());
            if(enemies.get(i).attack == true){
                player.hit();
            }
        }
        if(player.getLife() == true) {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                // interacts enemies and bullets
                for (int j = 0; j < bullets.size(); j++) {
                    int hp = e.health;
                    e.bulletColl(bullets.get(j));
                    if (hp != e.health) {
                        bullets.remove(j);
                        break;
                    }
                }
                // interacts player and bullets
                for(int j = 0; j < enemyBullets.size(); j++){
                    int hp = player.getHealth();
                    player.bulletColl(enemyBullets.get(j));
                    if( hp != player.getHealth()){
                        enemyBullets.remove(j);
                        break;
                    }
                }
                if (e.remove()) {
                    killedEnemies++;
                    enemies.remove(i);
                    i--;
                }

            }
        }
    }



    public static int getPlayerItems(){ return player.getItems(); }

    /**
     * Metoda dodaje nowych wrogów
     */
    public static void addEnemy(TileMap tileMap, int j, int i, boolean isHardEnemy){
        if(!isHardEnemy) {
            enemies.add(new EasyEnemy(tileMap, j * 32, i * 32));
        }
        else { enemies.add(new HardEnemy(tileMap, j * 32, i * 32)); }
    }

    /**
     * Metoda dodaje nowy pocisk dla bohatera
     */
    public static void addPlayerBullet(TileMap tMap, boolean changeWeapon){
        PlayState.bullets.add(new BulletPlayer(tMap, changeWeapon, player.getX(), player.getY(),
                player.getStayRight(), player.getStayLeft(), player.getLookUp(), player.getRun()));
    }

    /**
     * Metoda dodaje nowy pocisk dla wrogów
     */
    public static void addEnemyBullet(TileMap tileMap, double x, double y, boolean stayRight  ){
        enemyBullets.add(new BulletEnemy(tileMap ,x, y, stayRight));
    }


    /**
     * Metoda odpowiadająca za reakcję programu na naciśnięcie klawiszy podczas gry
     */
    public void keyPressed(KeyEvent keyEvent, int code){
        if(code == keyEvent.VK_LEFT){
            player.setStayLeft(true);
            player.setRun(true);
            player.setStayRight(false);
        }
        if(code == keyEvent.VK_UP){
            player.setLookUp(true);
        }
        if(code == keyEvent.VK_RIGHT){
            player.setStayRight(true);
            player.setRun(true);
            player.setStayLeft(false);
        }
        if(code == keyEvent.VK_SPACE){
            player.setJump(true);
        }
        if(code == keyEvent.VK_ESCAPE){
            GameP.isMenu = GameP.STATE.MENU;
        }
        if(code == keyEvent.VK_X){
            player.setFiring(true);
        }
    }


    /**
     *  Reakcja programu na zwolnienie klawiszy
     */
    public void keyReleased(KeyEvent keyEvent, int code) {
        if(code == keyEvent.VK_LEFT){
            player.setRun(false);
        }
        if(code == keyEvent.VK_RIGHT){
            player.setRun(false);
        }
        if(code == keyEvent.VK_X){
            player.setFiring(false);
        }
        if(code == keyEvent.VK_UP){
            player.setLookUp(false);
        }
        if(code == keyEvent.VK_SPACE){
            player.setJump(false);
        }
    }

}
