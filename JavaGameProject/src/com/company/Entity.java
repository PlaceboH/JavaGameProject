package com.company;

import java.awt.*;
import java.io.IOException;

public interface Entity {
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
    void draw(Graphics2D g) throws IOException, FontFormatException;
    void update();
    void Collision(int dir);
    void bulletColl(Bullet bullet);
}
