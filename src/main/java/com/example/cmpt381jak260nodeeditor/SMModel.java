package com.example.cmpt381jak260nodeeditor;

import java.util.ArrayList;

public class SMModel {

    private ArrayList<SMModelListener> subscribers;
    private ArrayList<SMStateNode> nodes;

    public SMModel(){
        this.subscribers = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    public void addNode(double x, double y){

        this.nodes.add(new SMStateNode(x, y));
        notifySubscribers();
    }

    public void addSubscriber(SMModelListener sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.modelChanged());
    }

    public ArrayList<SMStateNode> getNodes(){
        return this.nodes;
    }

    public boolean hitNode(double x, double y){
        for(SMStateNode n: this.nodes){
            if(n.contains(x, y)) return true;
        }
        return false;
    }

    public SMStateNode whichNode(double x, double y){
        for(SMStateNode n: this.nodes){
            if(n.contains(x, y)) return n;
        }
        return null;
    }

    public void moveNode(SMStateNode node, double dx, double dy){

        node.move(dx, dy);
        notifySubscribers();

    }
}
