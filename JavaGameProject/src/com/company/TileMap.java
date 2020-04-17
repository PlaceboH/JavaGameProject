package com.company;
import java.io.*;
import java.awt.*;

public class TileMap {
    private int x;
    private int y;
    private int tileSize;
    private int mapWidth;
    private int mapHeight;
    private int [][] map;


    public TileMap(String s, int tileSize){
        this.tileSize = tileSize;

        try{
            System.out.println("is good?");
            BufferedReader br = new BufferedReader(new FileReader(s));
            System.out.println("yes");
            mapWidth = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());

            System.out.println(mapWidth);

            map = new int[mapHeight][mapWidth];
            String delimiters = " ";
            for(int i = 0; i < mapHeight; i++){
                String line = br.readLine();
                String[] tokens = line.split(delimiters);
                for(int j = 0; j < mapWidth; j++){
                    map[i][j] = Integer.parseInt(tokens[j]);
                }
            }
        }
        catch (Exception e){
            System.out.println("NOOOOOO");
        }

    }

    public void update(){}

    public void draw(Graphics2D g){
        for(int i = 0; i < mapHeight; i++){
            for(int j = 0; j < mapWidth; j++){
                if(map[i][j] == 0){
                    g.setColor(Color.black);
                }
                else if(map[i][j] == 1){
                    g.setColor(Color.white);
                }
                else{
                    System.out.println("Error draw tails");
                }
                g.fillRect(x + j * tileSize, y + i * tileSize, tileSize, tileSize);

            }
        }
    }



}