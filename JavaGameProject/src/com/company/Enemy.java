package com.company;


/**
 * Klasa Enemy opisuje podstawowe właściwości wrogów EasyEnemy i HardEnemy
 */
class Enemy implements Entity {


    boolean attack = false;
    protected enum TYPE {EASY, HARD}
    protected static TYPE name;

    /**
     *  detected - jest prawdą gdy wróg widzi bohatera
     */
    protected boolean detected = false;

    /**
     * Prędkość spadania
     */
    protected double fallingSpeed;

    double afterHitSpeed;

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

    protected Position position;

    public boolean getStayRight(){ return position.stayRight; }

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
        position = new Position(0, 0, false, true, false);
        life = true;
        tileMap = tMap;
        afterHitSpeed = -0.02;
    }



    /**
     *  Metoda update jest odpowiedzialna za podstawową logike poruszania się wrogów
     */
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
            rect.left += position.dx;
            Collision(0);
            if (!onGround) {
                position.dy += fallingSpeed;
            }
            if(position.dx > 0){
                position.stayRight = true;
            }
            else {
                position.stayRight = false;
            }
        }
        rect.top += position.dy;
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
                    if ((position.dx > 0) && (dir == 0)) rect.left = j * sizeOfTile - rect.width;
                    if ((position.dx < 0) && (dir == 0)) rect.left = j * sizeOfTile + rect.width;
                    if ((position.dy > 0) && (dir == 1)) { rect.top = i * sizeOfTile - rect.height ; position.dy = 0; onGround = true; }
                    if ((position.dy < 0) && (dir == 1)) { rect.top = i * sizeOfTile + sizeOfTile; position.dy = 0; }
                    if(!detected) position.dx *= -1;
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
        if (x > rect.left && x < rect.left + rect.width  && y < rect.top * 2 && y > rect.top) {
            health -= bullet.getDamage();
            if(position.dx > 0) position.dx -= afterHitSpeed;
            else position.dx += afterHitSpeed;
        }
    }


}
