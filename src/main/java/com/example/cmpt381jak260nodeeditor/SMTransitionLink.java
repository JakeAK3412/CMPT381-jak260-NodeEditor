package com.example.cmpt381jak260nodeeditor;

public class SMTransitionLink extends SMItem{

    double startX, startY;
    double endX, endY;

    public boolean isTransition = true;

    SMStateNode first, second;

    public String event, sideEffects, context;



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

        this.event = "No Event";
        this.context = "No Context";
        this.sideEffects = "No Side Effects";
    }

    //Getters and Setters
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public boolean contains(double dx, double dy){
        //System.out.println("\ndx: " + dx + "\ndy: " + dy + "\nthis.x: " + this.x + "\nthis.y: " + this.y);
        if(dx >= this.x && dx <= this.x+120){
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

    //To note if it's a link that's been fully drawn and actually connects
    //Two nodes - if not, it will be deleted when the mouse is released
    @Override
    public boolean isTransition(){
        return this.isTransition;
    }
}
