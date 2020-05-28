package com.company;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.*;


/**
 *  Klasa dyrygująca GamePanel
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

    //my objects
    private static MenuState menuState;
    private static PlayState playState;
    private Achievements achievements;
    private Background backImg;



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
     * Metoda inicjalizuje wszystkie obiekty aplikacji
     */
    private void initialization(){
        menuState = new MenuState();
        achievements = new Achievements();
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) img.getGraphics();
        playState = new PlayState();
        backImg = new Background("JavaGameProject/Image/Back2.jpg", "JavaGameProject/Image/Back2.jpg");
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
                menuState.drawMenu(g, backImg);
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
        playState.update();
        achievements.setFindSecret(PlayState.player.getItems());
        achievements.setKilledEnemies(PlayState.killedEnemies);
    }

    /**
     * Metoda wywołuje metody wyświetlania obiektów gry
     */
    private void render(){
        backImg.draw(g);
        playState.draw(g);
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
            menuState.keyPressed(keyEvent, code);
        }
        else if(isMenu == STATE.PLAY){
            playState.keyPressed(keyEvent, code);
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
        playState.keyReleased(keyEvent, code);
    }
}
