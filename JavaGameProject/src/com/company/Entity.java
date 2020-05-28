package com.company;

/**
 * interfej Entity opisuje podstawowe charakterystyki dla bohatera i Wrogów
 */
public interface Entity {
    /**
     * Klasa opisuje wysokość, szerokość, położenie na osi ox i oy jednostki
     */
    class FloatRect{
        public float width;
        public float top;
        public float left;
        public float height;

        FloatRect(float left, float top, float width, float height ){
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
        }
    }
    /**
     *  logika poruszania się, zmiana położenia dla jednostki
     */
    void update();

    /**
     *  Zetknięcie się z przeszkodami na mapce
     */
    void Collision(int dir);
}
