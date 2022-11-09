package com.example.cmpt381jak260nodeeditor;

public class SMTransitionLink extends SMItem{

    double startX, startY;
    double endX, endY;

    public boolean isTransition = true;

    SMStateNode first, second;

    //The point on the transition node where the first arrow points to -
    //From there we can calculate the other side too
    double x, y;

    boolean isFinal;

    public SMTransitionLink(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.isFinal = false;
        this.isTransition = true;
    }

    @Override
    public boolean contains(double dx, double dy){
        System.out.println("\ndx: " + dx + "\ndy: " + dy + "\nthis.x: " + this.x + "\nthis.y: " + this.y);
        if(dx >= this.x-60 && dx <= this.x+60){
            if(dy >= this.y-60 && dy <= this.y+60){

                return true;
            }
        }

        return false;
    }



    @Override
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public boolean isTransition(){
        return this.isTransition;
    }
}
