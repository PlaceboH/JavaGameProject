package com.company;
import java.awt.*;

/**
 * Klasa Achievements wyświetla sekcję osiągnięcia gracza
 */
public class Achievements {

    /**
     *  findSecret jest true jeżeli gracz znajdzie 'osobliwy' przedmiot
     */
    private int secretItems;
    private int killedEnemies;


    public void setKilledEnemies(int killedEnemies){ this.killedEnemies = killedEnemies; }
    public void setFindSecret(int secretItems ){ this.secretItems = secretItems; }

    Achievements(){
        secretItems = 0;
        killedEnemies = 0;
    }

    /**
     * Metoda draw wyświetla osiągnięcia gracza
     * @param  g - obiekt wspomagający wyświetlaniu
     */
    public void draw(Graphics2D g){
        g.setColor(Color.CYAN);
        g.drawString("Secret items: " +  secretItems + "/3", 100, 100);
        g.drawString("Killed enemies: " + killedEnemies,  100, 200);
    }

}
