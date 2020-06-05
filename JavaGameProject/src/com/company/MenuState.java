package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class MenuState {

    /**
     * obiekt odpowiadający za Menu gry
     */
    private  Menu menu;

    MenuState(){
        menu = new Menu();
    }

    /**
     * Metoda wywołuje metody wyświetlania obiektów menu i backImg
     */
    public void drawMenu(Graphics2D g, Background backImg){
        backImg.draw(g);
        try {
            menu.draw(g);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda odpowiadająca za reakcję programu na naciśnięcie pewnych klawiszy w sekcji menu
     */
    public void keyPressed(KeyEvent keyEvent, int code){
        if(code == keyEvent.VK_UP){
            menu.moveUp();
        }
        if(code == keyEvent.VK_DOWN){
            menu.moveDown();
        }
        if(code == keyEvent.VK_ENTER) {
            if(menu.getSelectIndex() == 0) {
                GameP.isMenu = GameP.STATE.PLAY;
            }
            else if( menu.getSelectIndex() == 1){
                GameP.isMenu = GameP.STATE.ACHIVE;
            }
            else if( menu.getSelectIndex() == 2){
                System.exit(0);
            }
        }
    }
}
