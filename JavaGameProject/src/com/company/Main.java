package com.company;

import javax.swing.JFrame;



/**
 * Klasa Main uruchamia program
 */
public class Main {

    /**
     * @m main stwarza okno gry
     */
    public static void main(String[] args){
        JFrame wind = new JFrame();
        wind.setContentPane(new GameP());
        wind.setTitle("The Downward spiral");
        wind.setVisible(true);
        wind.setBounds(50, 50, 32*23, 512 );
        wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}