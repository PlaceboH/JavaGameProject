package com.company;

/**
 * interfejs opisujący przeciwników bohatera
 */
public interface Opponent extends Entity {
    void update(double px, double py, int pHealth);
    void bulletColl(BulletPlayer bullet);
}

