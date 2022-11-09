package com.example.cmpt381jak260nodeeditor;

public class SMStateNode extends SMItem{

    double x, y;

    public boolean isTransition;

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SMStateNode(double x, double y){
        this.x = x;
        this.y = y;
        this.isTransition = false;
        this.name = "Default";
    }

    public boolean contains(double cx, double cy){
        if(cx >= this.x-40 && cx <= this.x+40){
            if(cy >= this.y-30 && cy <= this.y+30){

                return true;
            }
        }

        return false;
    }

    public void move(double dx, double dy){

        x+= dx;
        y+= dy;

    }

    @Override
    public boolean isTransition(){
        return this.isTransition;
    }
}
