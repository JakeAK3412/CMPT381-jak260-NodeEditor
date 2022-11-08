package com.example.cmpt381jak260nodeeditor;

public class SMTransitionLink extends SMItem{

    double startX, startY;
    double endX, endY;

    SMStateNode first, second;

    boolean isFinal;

    public SMTransitionLink(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.isFinal = false;
    }
}
