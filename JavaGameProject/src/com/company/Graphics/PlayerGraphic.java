package com.company.Graphics;

import com.company.GameP;
import com.company.PlayState;

import javax.swing.*;
import java.awt.*;

/**
 *  klasa interfejsu bohatera
 */
public class PlayerGraphic {

    /**
     *  indeks poziomu
     */
    private int level;
    /**
     *  obrazak bohatera
     */
    Image playerImg;

    public PlayerGraphic(int level){
        this.level = level;
    }

    public void draw(Graphics2D g, int x, int y, int w, int h, int mapX, int playerHealth, boolean stRt){
        drawPlayer(g, x, y, w, h, mapX, stRt);
        drawMessage(g, playerHealth);
        playerImg = new ImageIcon("JavaGameProject/Image/bohater.png" ).getImage();
    }

    /**
     * Metoda draw wyświetla bohatera i linię zdrowia bohatera
     * @param  g - obiekt wspomagający wyświetlaniu
     */
    private void drawPlayer(Graphics2D g, int x, int y, int w, int h, int mapX, boolean stayRight) {
        if(stayRight){
            g.drawImage(playerImg, x-mapX, y, w, h, null);
        }
        else {
            g.drawImage(playerImg, x - mapX + w, y, -w , h, null);
        }
    }

    /**
     * metoda wyświetla linię zdrowia bohatera i indeks poziomu na ekranie
     */
    private void drawMessage(Graphics2D g, int playerHealth){
        g.setFont(new Font("Courier", Font.PLAIN, 32));
        g.setColor(Color.white);
        if(playerHealth > 20) {
            g.setColor(Color.orange);
        }
        else {
            g.setColor(Color.red);
        }
        if(level > 3) level = 3;
        g.fillRect(77, 25, playerHealth, 15);
        g.drawString("Hp  ", 35, 40);
        g.setColor(Color.WHITE);

        g.drawString("Level: " + level, 530, 50);

        if(playerHealth <= 0){
            g.setFont(new Font("Courier", Font.PLAIN, 70));
            g.drawString("Game Over", GameP.WIDTH/2 - 170,GameP.HEIGHT/2 - 70 );
        }
        if(PlayState.level == 4){
            g.setFont(new Font("Courier", Font.PLAIN, 70));
            g.drawString("You Win", GameP.WIDTH/2 - 170,GameP.HEIGHT/2 - 70 );
        }
    }


}
