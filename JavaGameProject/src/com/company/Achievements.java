package com.company;
import java.awt.*;

/**
 * Klasa Achievements wyświetla sekcję osiągnięcia gracza
 */
public class Achievements {
    /**
     *  x,y - połorzenie tekstu na ekranie
     */
    private int x, y;

    /**
     *  findSecret jest true jeżeli gracz znajdzie 'osobliwy' przedmiot
     */
    private boolean findSecret = false;

    /**
     *  przechowywuje słowa do wyświetlenia na ekranie
     */
    private String secret = "";

    Achievements(){}


    /**
     * Metoda draw wyświetla osiągnięcia gracza
     * @param  g - obiekt wspomagający wyświetlaniu
     */
    public void draw(Graphics2D g){
        g.setColor(Color.CYAN);
        if(findSecret) secret = "found";
        else secret = " - ";
        g.drawString("Secret item: " +  secret, 100, 100);
    }

}
