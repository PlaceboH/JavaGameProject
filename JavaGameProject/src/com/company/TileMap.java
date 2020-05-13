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
    protected char [][] map;

    public int getX(){ return  x; }
    public int getY(){ return  y; }
    public int getTileSize(){ return tileSize; }

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

    public void update(){
        if( GameP.player.getX() > GameP.WIDTH/2 && GameP.player.getX() < tileSize * mapWidth - GameP.WIDTH/2) {
            x = ((int) (GameP.player.getX() - GameP.WIDTH / 2));
        }
    }

    public void draw(Graphics2D g){
        for(int i = 0; i < mapHeight; i++){
            for(int j = 0; j < mapWidth; j++){
                if(map[i][j] == '1'){
                    g.drawImage(img, j * tileSize - x, i * tileSize, null);
                }
                else if(map[i][j] == ' ' || map[i][j] == '0'){
                    continue;
                }
                else if(map[i][j] == 'e'){
                    map[i][j] = ' ';
                    GameP.easyEnemies.add(new EasyEnemy(GameP.tileMap, j*32, i*32));
                }
                else{
                    System.out.println("Error draw tails");
                }

            }
        }
    }



}