package com.company;

import javax.swing.JFrame;


public class Main {

    public static void main(String[] args) {
        JFrame wind = new JFrame();
        wind.setContentPane(new GameP());
        wind.setTitle("Samolotyk");
        wind.setVisible(true);
        wind.setBounds(100, 100, 32*23, 512 );
        wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}