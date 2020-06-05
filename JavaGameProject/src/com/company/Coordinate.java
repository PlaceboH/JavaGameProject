package com.company;


/**
 * Klasa wspomagająca dla pracy z położeniem i rozmiarem obiektów
 */
public class Coordinate<T>{

    /**
     * x, y - połorzenie na osi x,y
     */
    public T x;
    public T y;
    /**
     * w - szerokość
     */
    public int w;
    /**
     * h - wysokość
     */
    public int h;

    Coordinate(T x, T y){
        this.x = x;
        this.y = y;
    }

    Coordinate(T x, T y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
