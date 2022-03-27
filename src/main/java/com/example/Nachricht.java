package com.example;

public class Nachricht {
    enum Actions{
        EVENT, LOST, FLAG, WIN, FULL, SET 
    };

    Actions a;
    int x, y, b;

    public Nachricht(Actions a, int x, int y, int b) {
        this.a = a;
        this.x = x;
        this.y = y;
        this.b = b;
    }

    public Nachricht(Actions a){
        this.a = a;
    }

    public Nachricht(Actions a, int x, int y){
        this.a = a;
        this.x = x;
        this.y = y;
    }

 

    public Actions getA() {
        return this.a;
    }

    public void setA(Actions a) {
        this.a = a;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getB() {
        return this.b;
    }

    public void setB(int b) {
        this.b = b;
    }



}
