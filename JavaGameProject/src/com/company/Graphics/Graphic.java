package com.company.Graphics;

import javax.swing.*;
import java.awt.*;

public class Graphic{

    /**
     * color - kolor pocisku
     */
    private Color color;
    Image enemyEasyImg;
    Image enemyHardImg;

    public Graphic(){
        enemyEasyImg = new ImageIcon("JavaGameProject/Image/EnemyEasy.png").getImage();
        enemyHardImg = new ImageIcon("JavaGameProject/Image/EnemyHard.png").getImage();
    }

    /**
     * Metoda draw wyświetla wrogów
     * @param  g - obiekt wspomagający wyświetlaniu
     */

    public void drawEnemy(Graphics2D g, int mapX, double x, double y, double w, double h, boolean stayRight) {
        if(h < 50) {
            if (stayRight) {
                g.drawImage(enemyEasyImg, (int) x - mapX, (int) y, (int) w, (int) h, null);
            } else {
                g.drawImage(enemyEasyImg, (int) (x - mapX + w), (int) y, (int) -w, (int) h, null);
            }
        }
        else {
            if (stayRight) {
                g.drawImage(enemyHardImg, (int) x - mapX, (int) y, (int) w, (int) h, null);
            } else {
                g.drawImage(enemyHardImg, (int) (x - mapX + w), (int) y, (int) -w, (int) h, null);
            }
        }
    }

    /**
     * Metoda draw wyświetla pocisk dla enemy
     * @param  g - obiekt wspomagający wyświetlaniu
     */
    public void drawBulletEasyEnemy(Graphics2D g, int mapX, int mapY, double x, double y, int w, int h){
        color = Color.black;
        g.setColor(color);
        g.fillRect((int)x - mapX, (int)y - mapY, w, h);
    }


    /**
     * Metoda draw wyświetla pocisk dla gracza
     * @param  g - obiekt wspomagający wyświetlaniu
     */
    public void drawPlayerBullet(Graphics2D g, int mapX, int mapY ,double x, double y, int w, int h) {
        color = Color.green;
        g.setColor(color);
        g.fillRect((int)x - mapX, (int)y - mapY, w, h);
    }
}
