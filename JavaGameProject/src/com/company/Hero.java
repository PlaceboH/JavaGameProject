package com.company;

/**
 *  interfejs dla bohaterów(w danym programie jednego) gry
 */
public interface Hero extends Entity {
    void hit();
    void update();
    void bulletColl(BulletEnemy bullet);
}
