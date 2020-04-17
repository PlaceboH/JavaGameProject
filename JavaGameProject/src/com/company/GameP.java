package com.company;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GameP extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 32 * 23;
    public static final int HEIGHT = 512;

    // thread
    private Thread thread;
    private boolean isRun;
    private int FPS = 60;
    private long targetTime = 1000/FPS;

    // img
    private BufferedImage img;
    private Graphics2D g;

    // my objects
    private TileMap tileMap;
    private Player player;

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
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) img.getGraphics();
        tileMap = new TileMap("C:\\Users\\placebo\\IdeaProjects\\Contra\\src\\com\\company\\map1.txt", 32);
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

            update();
            render();
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
        tileMap.draw(g);
        player.draw(g);
    }

    private void draw(){
        Graphics g2 = getGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        if(code == keyEvent.VK_LEFT){
            player.setStayLeft(true);
        }
        if(code == keyEvent.VK_RIGHT){
            player.setStayRight(true);
        }
        if(code == keyEvent.VK_SPACE){
            player.setJump(true);
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
