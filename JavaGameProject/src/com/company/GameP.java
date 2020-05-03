package com.company;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;

public class GameP extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 32 * 23;
    public static final int HEIGHT = 512;

    public enum STATE {PLAY, MENU}
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
    private TileMap tileMap;
    private Player player;
    private Menu menu;


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

    public void initialization(){
        isRun = true;
        menu = new Menu();
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) img.getGraphics();
        tileMap = new TileMap("JavaGameProject/src/com/company/map1.txt", 32);
        backImg = new Background("JavaGameProject/Image/Back2.jpg", "JavaGameProject/Image/Lif9.gif");
        player = new Player(tileMap);
        player.setX(250);
        player.setY(200);
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

    //////////////////////////////////
    private void update(){
        tileMap.update();
        player.update();
    }

    private void render(){
        backImg.draw(g);
        tileMap.draw(g);
        player.draw(g);
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

                }
                else if( menu.getSelectIndex() == 2){
                    System.exit(0);
                }
            }
        }
        else{
            if(code == keyEvent.VK_LEFT){
                player.setStayLeft(true);
            }
            if(code == keyEvent.VK_RIGHT){
                player.setStayRight(true);
            }
            if(code == keyEvent.VK_SPACE){
                    player.setJump(true);
            }
            if(code == keyEvent.VK_ESCAPE){
                isMenu = STATE.MENU;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        if(code == keyEvent.VK_LEFT){
            player.setStayLeft(false);
        }
        if(code == keyEvent.VK_RIGHT){
            player.setStayRight(false);
        }

    }
}
