package com.company;

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


}
