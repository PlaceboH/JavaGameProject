package com.company;

class Position{
    public double dx;
    public double dy;
    public boolean onGround;
    public boolean stayRight;
    public boolean stayLeft;

    Position(double dx, double dy, boolean onGround, boolean stayLeft, boolean stayRight){
        this.dx = dx;
        this.dy = dy;
        this.onGround = onGround;
        this.stayRight = stayRight;
        this.stayLeft = stayLeft;
    }
}