package com.company;

import java.awt.*;


public class Achievements {
    private int x;
    private int y;
    private int h;
    private int w;
    private boolean findSecret = false;
    private String secret = "";

    Achievements(){}

    public void draw(Graphics2D g){
        g.setColor(Color.CYAN);
        if(findSecret) secret = "found";
        else secret = " - ";
        g.drawString("Secret item: " +  secret, 100, 100);
    }

}
