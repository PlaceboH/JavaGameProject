package com.company;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 *  Klasa Menu jest odpowiedzialna za menu gry
 */
public class Menu {
    /**
     *  x,y - początkowe położenie tekstu
     */
    private int x, y;

    /**
     *  odstęp wg osi oy między kolejnym tekstem
     */
    private int height;

    /**
     *  tablica potrzebna do przechowywania słów wyświetlających się w menu
     */
    private String[] list  = new String[3];

    /**
     *  indeks tablicy list
     */
    private int selectIndex;


    /**
     *  Konstruktor
     */
    Menu(){
        selectIndex = 0;
        height = 50;
        x = 32*23/2 ;
        y = 90;
        list[0] = "Play";
        list[1] = "Achievements";
        list[2] = "Exit";
    }


    /**
     * @return selectIndex
     */
    public int getSelectIndex() { return selectIndex; }

    /**
     * Wyświetlanie menu na ekranie
     * @exception IOException - niepoprawnie podany adres
     * @exception FontFormatException - uwiadomienie o tym czemu font nie został podtwierdzony
     * @param  g - obiekt wspomagający wyświetlaniu
     */
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
            g.drawString(list[i], x - (int)(length/2) , (y)*(i+1) + height);
        }

    }

    /**
     *  zmiana indeksu tablicy o jeden do tyłu
     */
    public void moveUp(){
        if(selectIndex > 0) {
            selectIndex--;
        }
        else {
            selectIndex = list.length - 1;
        }
    }

    /**
     *  zmiana indeksu tablicy o jeden do przodu
     */
    public void moveDown(){
        if(selectIndex < list.length - 1) {
            selectIndex++;
        }
        else{
            selectIndex = 0;
        }
    }


}
