package com.company;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;


/**
 *  Klasa dyrygująca
 */
public class GameP extends JPanel implements Runnable, KeyListener {

    /**
     * szerokość okna aplikacji
     */
    public static final int WIDTH = 32 * 23;

    /**
     * wysokość okienka aplikacji
     */
    public static final int HEIGHT = 512;

    /**
     *  możliwe stany w kórym może być aplikacja
     */
    public enum STATE {PLAY, MENU, ACHIVE}

    /**
     *  stan aplikacji
     */
    public static STATE isMenu = STATE.MENU;

    /**
     * Wątek potrzebny do umożliwienia reakcji interfejsu na działalność użytkownika
      przy przetwarzaniu pewnej informacji
     */
    private Thread thread;

    /**
     *  Ilość klatek na sekundę
     */
    private int FPS = 60;
    private long targetTime = 1000/FPS;
    // img
    private BufferedImage img;
    private Graphics2D g;
    private Background backImg;
    // my objects
    private  Menu menu;
    private Achievements achievements;
    public static TileMap tileMap;
    public static Player player;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<BulletPlayer> bullets;
    public static ArrayList<BulletEnemy> enemyBullets;



    public GameP(){
        super();
        setFocusable(true);
        requestFocus();
    }

    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    /**
     * Metoda inicjalizuje wszystkie obiekty aplikacji( oprócz GameP oczywiście )
     */
    private void initialization(){
        menu = new Menu();
        achievements = new Achievements();
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) img.getGraphics();
        tileMap = new TileMap("JavaGameProject/src/com/company/map1.txt", 32);
        backImg = new Background("JavaGameProject/Image/Back2.jpg", "JavaGameProject/Image/Back2.jpg");
        player = new Player();
        player.setX(250);
        player.setY(200);
        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<BulletPlayer>();
        enemyBullets = new ArrayList<BulletEnemy>();
    }



    /**
     *  Metoda run inicjalizuje główną pętlę gry
     */
    @Override
    public void run() {
        initialization();
        long startTimer;
        long elapsedTimer;
        long pausedTimer;
        while (true){
            startTimer = System.nanoTime();
            if(isMenu == STATE.MENU){
                renderMenu();
            }
            else if(isMenu == STATE.ACHIVE){
                renderAchievements();
            }
            else{
                update();
                render();
            }
            draw();

            elapsedTimer = (System.nanoTime() - startTimer)/1000000;
            pausedTimer = Math.abs(targetTime - elapsedTimer);

            try{
                Thread.sleep(pausedTimer);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }


    /**
     *  Metoda update wywołuje metody update obiektów tileMap, player, bullet, enemies, enemyBullet
     */
    private void update(){
        tileMap.update();
        player.update();
        //   Player bullets update
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


        for(int i = 0; i < enemies.size(); i++ ){
            enemies.get(i).update();
            boolean remove = enemies.get(i).remove();
            if(remove == true){
                enemies.remove(i);
                i--;
            }
        }
        if(player.getLife() == true) {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                for (int j = 0; j < bullets.size(); j++) {
                    int hp = e.health;
                    e.bulletColl(bullets.get(j));
                    if (hp != e.health) {
                        bullets.remove(j);
                        break;
                    }
                }
                for(int j = 0; j < enemyBullets.size(); j++){
                    int hp = player.health;
                    player.bulletColl(enemyBullets.get(j));
                    if( hp != player.health){
                        enemyBullets.remove(j);
                        break;
                    }
                }
                if (e.remove() == true) {
                    enemies.remove(i);
                    i--;
                }

            }
        }
    }

    /**
     * Metoda wywołuje metody wyświetlania obiektów gry
     */
    private void render(){
        backImg.draw(g);
        tileMap.draw(g);
        for(int i = 0; i < bullets.size(); i++ ){
            bullets.get(i).draw(g);
        }
        for(int i = 0; i < enemyBullets.size(); i++ ){
            enemyBullets.get(i).draw(g);
        }
        player.draw(g);

        for(int i = 0; i < enemies.size(); i++ ){
            enemies.get(i).draw(g);
        }
    }


    /**
     * Metoda wywołuje metody wyświetlania obiektów menu i backImg
     */
    private void renderMenu(){
        try {
            backImg.draw(g);
            menu.draw(g);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda wywołuje metody wyświetlania obiektów achievements i backImg
     */
    private void renderAchievements(){
        backImg.draw(g);
        achievements.draw(g);
    }


    private void draw(){
        Graphics g2 = getGraphics(); // from graphics2d to graphics
        g2.drawImage(img, 0, 0, null);
        g2.dispose(); // method for gc
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}


    /**
     * Metoda odpowiadająca za reakcję programu na naciśnięcie pewnych klawiszy
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        if(isMenu == STATE.MENU){
            if(code == keyEvent.VK_UP){
                menu.moveUp();
            }
            if(code == keyEvent.VK_DOWN){
                menu.moveDown();
            }
            if(code == keyEvent.VK_ENTER) {
                if(menu.getSelectIndex() == 0) {
                    isMenu = STATE.PLAY;
                }
                else if( menu.getSelectIndex() == 1){
                    isMenu = STATE.ACHIVE;
                }
                else if( menu.getSelectIndex() == 2){
                    System.exit(0);
                }
            }
        }
        else if(isMenu == STATE.PLAY){
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
                isMenu = STATE.MENU;
            }
            if(code == keyEvent.VK_X){
                player.setFiring(true);
            }
        }
        else if( isMenu == STATE.ACHIVE){
            if(code == keyEvent.VK_ESCAPE){
                isMenu = STATE.MENU;
            }
        }

    }

    /**
     *  Reakcja programu na zwolnienie pewnych klawiszy
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
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
