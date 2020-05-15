package com.company;

import javax.swing.*;
import java.awt.*;


/**
 * Klasa Background służy do wyświetlania obrazków na tło
 */
public class Background {
    /**
     * img, menuImeg potrzebne dla przechowywania obrazków
     */
    private Image img, menuImg;

    /**
     * Konstruktor
     * @param s - adres dla obrazku
     * @param ms - adres dla obrazku dla menu
     */
    Background(String s, String ms){
        img = new ImageIcon(s).getImage();
        menuImg = new ImageIcon(ms).getImage();
    }

    /**
     * Metoda draw wyświetla obrazek na tło
     * @param  g - obiekt wspomagający wyświetlaniu
     */
    public void draw(Graphics2D g){
        if(GameP.isMenu.equals(GameP.STATE.MENU)) {
            g.drawImage(menuImg, -400, -100, null);
        }
        else if(GameP.isMenu.equals(GameP.STATE.ACHIVE)){
            //    menuImg.getScaledInstance(1000, 1000, 1);
            g.drawImage(menuImg, 0, -200, null);
        }
        else {
            g.drawImage(img, 0, 0, null);
        }
    }


}
