package com.company;


/**
 * Klasa Enemy opisuje podstawowe właściwości wrogów EasyEnemy i HardEnemy
 */
class Enemy implements Opponent {


    boolean attack = false;
    protected enum TYPE {EASY, HARD}
    protected static TYPE name;

    /**
     *  stayRight - jest prawdą gdy wróg 'patrzy' w prawą stronę
     */
    protected boolean stayRight = false;
    /**
     *  detected - jest prawdą gdy wróg widzi bohatera
     */
    protected boolean detected = false;

    /**
     * Prędkość spadania
     */
    protected double fallingSpeed;

    /**
     *  dx - prądkość poruszania się w lewo, prawo
     *  dy - predkość poruszania się w dół, górę
     */
    protected double dx,dy;

    /**
     *  onGround - jest prawdą gdy wróg stoi na ziemi
     */
    protected boolean onGround;

    /**
     *  Czy wróg żyje
     */
    protected boolean life;
    /**
     *  zdrowie wrogów
     */
    protected int health;

    /**
     *  połorzenie (x,y) i rozmir (width, height)
     */
    protected FloatRect rect;
    protected TileMap tileMap;



    /**
     * Konstruktor nadaje enemy rozmiar i położenie
     * @param tMap - mapka
     * @param x - połorzenie wg osi x enemy
     * @param y - połorzemie wg osi y enemy
     * @param w - szerokość wroga
     * @param h - wysokość wroga
     */
    Enemy(TileMap tMap, int x, int y, int w, int h){
        rect = new FloatRect(x, y, w, h);
        life = true;
        tileMap = tMap;
    }



    /**
     *  Metoda update jest odpowiedzialna za podstawową logike poruszania się wrogów
     */
    @Override
    public void update(double px, double py, int pHealth) {


        if(pHealth > 0){
            double ex = rect.left;
            double ey = rect.top;
            double distX = ex - px;
            double distY = ey - py;
            double dist = Math.sqrt(distX * distX + distY * distY);
            if ((int) dist <= 32) {
                attack = true;
            }
            else{ attack = false; }
        }

        if(life) {
            rect.left += dx;
            Collision(0);
            if (!onGround) {
                dy += fallingSpeed;
            }
            if(dx > 0){
                stayRight = true;
            }
            else {
                stayRight = false;
            }
        }
        rect.top += dy;
        onGround = false;
        Collision(1);
    }

    /**
     * @return false - jeżeli życie wroga jest większe od zera
     * @return true - w przeciwnym przypadku
     */
    public boolean remove(){
        if(health > 0){
            return false;
        }
        else { return  true; }
    }


    /**
     * Zetknięcie się wrogów z przeszkodami na mapie
     * @param dir - oś ox dir = 0  lub oy dla dir = 1
     */
    @Override
    public void Collision(int dir) {
        int sizeOfTile = tileMap.getTileSize();
        for (int i = (int)rect.top / sizeOfTile; i < (rect.top + rect.height) / sizeOfTile; i++) {
            for (int j = (int)rect.left / sizeOfTile; j < (rect.left + rect.width) / sizeOfTile; j++) {
                if (tileMap.map[i][j] == 'B' || tileMap.map[i][j] == 'K' || tileMap.map[i][j] == 'L' || tileMap.map[i][j] == 'W' ){
                    if ((dx > 0) && (dir == 0)) rect.left = j * sizeOfTile - rect.width;
                    if ((dx < 0) && (dir == 0)) rect.left = j * sizeOfTile + rect.width;
                    if ((dy > 0) && (dir == 1)) { rect.top = i * sizeOfTile - rect.height ; dy = 0; onGround = true; }
                    if ((dy < 0) && (dir == 1)) { rect.top = i * sizeOfTile + sizeOfTile; dy = 0; }
                    if(!detected) dx *= -1;
                }

            }
        }
    }

    /**
     * Sprawdza czy pocisk bohatera trafił w enemy
     * @param bullet - pocisk bohatera
     */
    public void bulletColl(BulletPlayer bullet) {
        int x  = (int)((double)bullet.rect.x);
        int y = (int)((double)bullet.rect.y);
        if (x > rect.left && x < rect.left + 32  && y < rect.top * 2 && y > rect.top) {
            health -= bullet.getDamage();
            if(dx > 0) dx -= 0.02;
            else dx += 0.02;
        }
    }


}
