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

public class GameP extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 32 * 23;
    public static final int HEIGHT = 512;

    public enum STATE {PLAY, MENU, ACHIVE}
    public static STATE isMenu = STATE.MENU;

    // thread
    private Thread thread;
    private boolean isRun;
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
    public static ArrayList<EasyEnemy> easyEnemies;
    public static ArrayList<BulletPlayer> bullets;
    public static ArrayList<BulletEnemy> enemyBullets;


    public GameP(){
        super();
        setPreferredSize(new Dimension(WIDTH , HEIGHT));
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

    private void initialization(){
        isRun = true;
        menu = new Menu();
        achievements = new Achievements();
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) img.getGraphics();
        tileMap = new TileMap("JavaGameProject/src/com/company/map1.txt", 32);
        backImg = new Background("JavaGameProject/Image/Back2.jpg", "JavaGameProject/Image/Lif9.gif");
        player = new Player(tileMap);
        player.setX(250);
        player.setY(200);
        easyEnemies = new ArrayList<EasyEnemy>();
        bullets = new ArrayList<BulletPlayer>();
        enemyBullets = new ArrayList<BulletEnemy>();
    }

    @Override
    public void run() {
        initialization();
        long startTimer;
        long elapsedTimer;
        long pausedTimer;
        while (isRun){
            startTimer = System.nanoTime();
            if(isMenu == STATE.MENU){
                try {
                    backImg.draw(g);
                    menu.draw(g);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FontFormatException e) {
                    e.printStackTrace();
                }
            }
            else if(isMenu == STATE.ACHIVE){
                backImg.draw(g);
                achievements.draw(g);
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


        for(int i = 0; i < easyEnemies.size(); i++ ){
            easyEnemies.get(i).update();
            boolean remove = easyEnemies.get(i).remove();
            if(remove == true){
                easyEnemies.remove(i);
                i--;
            }
        }
        if(player.getLife() == true) {
            for (int i = 0; i < easyEnemies.size(); i++) {
                Enemy e = easyEnemies.get(i);
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
                    easyEnemies.remove(i);
                    i--;
                }

            }
        }
    }

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

        for(int i = 0; i < easyEnemies.size(); i++ ){
            easyEnemies.get(i).draw(g);
        }
    }

    private void draw(){
        Graphics g2 = getGraphics(); // from graphics2d to graphics
        g2.drawImage(img, 0, 0, null);
        g2.dispose(); // method for gc
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        if(isMenu == STATE.MENU){
            if(code == keyEvent.VK_UP){
                menu.moveUp(g);
            }
            if(code == keyEvent.VK_DOWN){
                menu.moveDown(g);
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

    }
}
