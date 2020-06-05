package com.company;
import javax.swing.*;
import java.io.*;
import java.awt.*;

/**
 *  Klasa TileMap jest odpowiedzialna za stworzenie mapy dla gry
 */
public class TileMap {

    /**
     *  x,y - współrzędne 'kamery', czyli ten kawałek mapki, który widzi użytkownik
     */
    private int x, y;

    /**
     *  Rozmiar jednostki tekstury(poligonu)
     */
    private int tileSize;

    /**
     *  Szerokość i wysokość mapki
     */
    private int mapWidth, mapHeight;;

    /**
     *  obrazeki tekstury(poligonu)
     */
    private Image imgT;
    private Image imgTree;
    private Image imgB;
    private Image imgL;
    private Image imgW;

    /**
     *  Tablica znaków mapki
     */
    protected char [][] map;

    /**
     * @return x
     */
    public int getX(){ return  x; }

    /**
     * @return y
     */
    public int getY(){ return  y; }

    /**
     * @return tileSize
     */
    public int getTileSize(){ return tileSize; }
    public int getMapWidth() { return mapWidth; }


    /**
     * Konstruktor wyznacza rozmiary mapki i wypełnia tablicę znaków(map)
     * @param tileSize - zadany rozmiar poligonu
     * @param s - link na txt plik z mapką
     */
    public TileMap(String s, int tileSize){
        this.tileSize = tileSize;
        imgB = new ImageIcon("JavaGameProject/Poligons/poligonB.png").getImage();
        imgL = new ImageIcon("JavaGameProject/Poligons/poligonL.png").getImage();
        imgTree = new ImageIcon("JavaGameProject/Poligons/poligonTree.png").getImage();
        imgT = new ImageIcon("JavaGameProject/Poligons/poligonT.png").getImage();
        imgW = new ImageIcon("JavaGameProject/Poligons/poligonW.png").getImage();


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

    /**
     *  Przesunięcie kamery za graczem (wzdłuż osi ox)
     */
    public void update( double playerPositionX){
        if( playerPositionX > GameP.WIDTH/2 && playerPositionX < tileSize * mapWidth - GameP.WIDTH/2) {
            x = ((int) (playerPositionX - GameP.WIDTH / 2));
        }
    }

    /**
     *  Wyświetlenie elementów mapki na ekranie
     *  @param  g - obiekt wspomagający wyświetlaniu
     */
    public void draw(Graphics2D g){
        for(int i = 0; i < mapHeight; i++){
            for(int j = 0; j < mapWidth; j++){
                if(map[i][j] == 'B'){
                    g.drawImage(imgB, j * tileSize - x, i * tileSize, null);
                }
                else if(map[i][j] == 'L'){
                    g.drawImage(imgL, j * tileSize - x, i * tileSize, null);
                }
                else if(map[i][j] == 'W'){
                    g.drawImage(imgW, j * tileSize - x, i * tileSize, null);
                }
                else if(map[i][j] == 'T'){
                    g.drawImage(imgTree, j * tileSize - x, i * tileSize - 40, null);
                }
                else if(map[i][j] == 't'){
                    g.drawImage(imgT, j * tileSize - x, i * tileSize, null);
                }
                else if(map[i][j] == 'g'){
                    g.setColor(Color.black);
                    g.fillRect(j * tileSize - x + 16, i * tileSize, 8, 8);
                }
                else if(map[i][j] == 's'){
                    g.setColor(Color.yellow);
                    g.fillOval(j * tileSize - x + 16, i * tileSize, 8, 8);
                }
                else if(map[i][j] == ' ' || map[i][j] == '0'){
                    continue;
                }
                else if(map[i][j] == 'e'){
                    map[i][j] = ' ';
                    PlayState.addEnemy( this, j, i, false);
                }
                else if(map[i][j] == 'E'){
                    map[i][j] = ' ';
                    PlayState.addEnemy( this, j, i, true);
                }
                else{
                    System.out.println("Error draw tails");
                }

            }
        }
    }



}