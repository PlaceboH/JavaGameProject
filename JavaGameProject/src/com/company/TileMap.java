package com.company;
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class TileMap {
    private int x;
    private int y;
    private int tileSize;
    private int mapWidth;
    private Image img;
    private int mapHeight;
    public char [][] map;


    public TileMap(String s, int tileSize){
        this.tileSize = tileSize;
        img = new ImageIcon("JavaGameProject/Image/plates.png").getImage();
        try{
            BufferedReader br = new BufferedReader(new FileReader(s));
            mapWidth = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());

            map = new char[mapHeight][mapWidth];
            for(int i = 0; i < mapHeight; i++){
                String line = br.readLine();
                for(int j = 0; j < mapWidth; j++){
                    map[i][j] = line.charAt(j);
                }
            }
        }
        catch (Exception e){
            System.out.println("Bad link to files!");
        }

    }

    public void update(){}

    public int getMapWidth(){ return mapWidth; }
    public int getX(){ return  x; }
    public int getY(){ return  y; }
    public int getCol(int x){ return  x / tileSize; }
    public int getRow(int y){ return  y /tileSize; }
    public int getTileSize(){ return tileSize; }
    public void setX(int sx){ x = sx; }
    public void setY(int sy){ y = sy; }



    public void draw(Graphics2D g){
        for(int i = 0; i < mapHeight; i++){
            for(int j = 0; j < mapWidth; j++){
                if(map[i][j] == '1'){
                    g.setColor(Color.black);
                }
                else if(map[i][j] == ' ' || map[i][j] == '0'){
                    continue;
                }
                else{
                    System.out.println("Error draw tails");
                }
                //g.fillRect( j * tileSize - x,  i * tileSize, tileSize, tileSize);

                g.drawImage(img, j * tileSize - x, i * tileSize, null);
            }
        }
    }



}