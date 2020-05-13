package com.company;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Menu {
    private int x;
    private int y;
    private int height;
    private String[] list  = new String[3];
    private int selectIndex;

    Menu(){
        selectIndex = 0;
        height = 100;
        x = 32*23/2 ;
        y = 90;
        list[0] = "Play";
        list[1] = "Achievements";
        list[2] = "Exit";
    }

    public int getSelectIndex() { return selectIndex; }

    public void draw(Graphics2D g) throws IOException, FontFormatException {
        for(int i = 0; i < list.length; i++){

            if(i == selectIndex){
                g.setColor(Color.orange);
            }
            else{
                g.setColor(Color.CYAN);
            }

            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("JavaGameProject/Fonts/Funkrocker.otf")).deriveFont(52f);
            g.setFont(customFont);
            long length = (int) g.getFontMetrics().getStringBounds(list[i], g).getWidth();
            g.drawString(list[i], x - (int)(length/length) + 155, (y)*(i+1) + height/2);
        }

    }

    public void moveUp(Graphics2D g){
        if(selectIndex > 0) {
            selectIndex--;
        }
        else {
            selectIndex = list.length - 1;
        }
    }
    public void moveDown(Graphics2D g){
        if(selectIndex < list.length - 1) {
            selectIndex++;
        }
        else{
            selectIndex = 0;
        }
    }


}
