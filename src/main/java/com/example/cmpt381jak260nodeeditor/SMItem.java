package com.example.cmpt381jak260nodeeditor;

public abstract class SMItem {

    //double x, y;
    public abstract boolean contains(double dx, double dy);
    public abstract void move(double dx, double dy);
    public abstract boolean isTransition();
}
