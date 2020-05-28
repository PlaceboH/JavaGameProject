package com.company;

public class Coordinate<T>{
    public T x;
    public T y;
    public int w;
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
