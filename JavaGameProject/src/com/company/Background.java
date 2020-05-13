package com.company;

import javax.swing.*;
import java.awt.*;

public class Background {
    private Image img;
    private Image menuImg;
    private Image menuImg2;
    Background(String s, String ms){
        img = new ImageIcon(s).getImage();
        menuImg = new ImageIcon(ms).getImage();
        menuImg2 = new ImageIcon("JavaGameProject/Image/Back2.jpg").getImage();

    }

    public void draw(Graphics2D g){
        if(GameP.isMenu.equals(GameP.STATE.MENU)) {
            g.drawImage(menuImg2, 100, -30, null);
            g.drawImage(menuImg, 0, -100, null);
        }
        else if(GameP.isMenu.equals(GameP.STATE.ACHIVE)){
            //    menuImg.getScaledInstance(1000, 1000, 1);
            g.drawImage(menuImg2, 0, -100, null);
        }
        else {
            g.drawImage(img, 0, 0, null);
        }
    }


}
