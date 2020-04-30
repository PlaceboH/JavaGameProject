package com.company;

import javax.swing.*;
import java.awt.*;

public class Background {
    private Image img;
    private Image menuImg;
    Background(String s, String ms){
        img = new ImageIcon(s).getImage();
        menuImg = new ImageIcon(ms).getImage();

    }

    public void draw(Graphics2D g){
        g.drawImage(img, 0, 0, null);
    }
    public void drawMenu(Graphics2D g){
        g.drawImage(menuImg, 0, 0, null);
    }

}
