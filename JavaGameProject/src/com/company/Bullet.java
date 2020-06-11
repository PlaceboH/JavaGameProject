package com.company;

/**
 * Klasa Bulet opisuje podstawowe właściwości pocisków
 */
public class Bullet {

    private TileMap tMap;
    /**
     *  SpeedX - prędkość pocisku wzdłuż osi x
     *  SpeedY - prędkość pocisku wzdłuż osi y
     */
    protected int speedX, speedY;

    /**
     *  damage - zniszczalność pocisku
     */
    protected int damage;

    /**
     * x,y połorzenie pocisku i rozmiary pocisku
     */
    protected Coordinate<Double> rect;

    /**
     * @return damage
     */
    public int getDamage() {
        return damage;
    }


    Bullet(TileMap tileMap){
        tMap = tileMap;
        rect = new Coordinate<Double>(0.0, 0.0);
    }


    /**
     * Metoda update zmienia połorzenie pocisków
     */
    public void update(){
        rect.x += speedX;
        rect.y += speedY;
    }


    /**
     *  Metoda remove sprawdza czy pocisk nie trafił w przeszkodę
     * @return true jeżeli pocisk trafił w przeszkodę na mapie
     */
    public boolean remove(){
        int tileSize =  tMap.getTileSize();
        double y = rect.y;
        double x = rect.x;
        for (int i = (int)y / tileSize; i < (y + rect.h) / tileSize; i++) {
            for (int j = (int)x / tileSize; j < (x + rect.w) / tileSize; j++) {
                if (tMap.map[i][j] == 'B'
                        || tMap.map[i][j] == 'K'
                        || tMap.map[i][j] == 'L'
                        || tMap.map[i][j] == 'W'
                        || tMap.map[i][j] == '0'){
                    return true;
                }
            }
        }
        return false;
    }

}
