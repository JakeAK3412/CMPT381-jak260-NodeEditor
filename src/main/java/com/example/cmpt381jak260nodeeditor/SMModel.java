package com.example.cmpt381jak260nodeeditor;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class SMModel {

    private ArrayList<SMModelListener> subscribers;
    private ArrayList<SMStateNode> nodes;
    private ArrayList<SMTransitionLink> links;

    public SMModel(){
        this.subscribers = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.links = new ArrayList<>();
    }


    public void setContextName(String context, SMTransitionLink node){
        node.setContext(context);
        notifySubscribers();

    }
    public void setSideEffectName(String event, SMTransitionLink node){
        node.setSideEffects(event);
        notifySubscribers();

    }
    public void setEventName(String event, SMTransitionLink node){
        node.setEvent(event);
        notifySubscribers();

    }

    public void setStateName(String state, SMStateNode node){
        node.setName(state);
        notifySubscribers();

    }

    public SMTransitionLink addLink(double startx, double starty, double endx, double endy){
        SMTransitionLink link = new SMTransitionLink(startx, starty, endx, endy);
        this.links.add(link);
        notifySubscribers();
        return link;
    }

    public void removeLink(SMTransitionLink link){
        if(link != null) {
            this.links.remove(link);
            notifySubscribers();
        }
    }

    public ArrayList<SMTransitionLink> getLinks(){
        return this.links;
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

        //Also check if we hit a transition node:
        for(SMTransitionLink l: this.links){
            //System.out.println("checked a link");
            if(l.contains(x, y)) return true;
        }
        return false;
    }

    public SMItem whichNode(double x, double y){
        for(SMItem n: this.nodes){
            if(n.contains(x, y)) return n;
        }
        //Check if it's a transition node too:
        for(SMItem l: this.links){
            if(l.contains(x, y)) return l;
        }
        return null;
    }

    /**
     * To figure out the best way to draw a final link between
     * two nodes that just touches the borders - after mouse release
     * @param first
     * @param second
     * @return
     */
    public double[] bestLink(SMStateNode first, SMStateNode second){

        double[] values = {0, 0, 0, 0};

        if(first.x <= second.x){
            if(first.y <= second.y){
                //If second is to the right and bottom
                //values = {first.x+40, first.y+30, second.x-40, second.y+30};
                values[0] = first.x+40;
                values[1] = first.y;
                values[2] = second.x-40;
                values[3] = second.y;
            }
            else{
                //to the right and top
                values[0] = first.x+40;
                values[1] = first.y;
                values[2] = second.x-40;
                values[3] = second.y;
            }
        }
        else{
            //to the left:
            values[0] = first.x-40;
            values[1] = first.y;
            values[2] = second.x+40;
            values[3] = second.y;

        }


        return values;
    }

    /**
     * Makes a link a final link so the view knows to draw a transition node
     *
     */
    public void makeFinalLink(SMTransitionLink link){

        //Two different cases, if the link is a link between two
        //different nodes or the same node:


        link.isFinal = true;

        if(!link.first.equals(link.second)) {

            double[] coords = this.bestLink(link.first, link.second);
            System.out.println(coords[0]);

            //Set the coords of the transition node, doing some trig:


            double distX = link.endX - link.startX;
            double distY = link.endY - link.startY;
            //Get the length
            //System.out.println("\nPoint 1: " + x1  +", " + y1 + "\nPoint 2: " + x2 + ", " + y2);
            double length = Math.sqrt(distX * distX + distY * distY);
            //System.out.println(length);
            //Get the angle
            double angle = Math.atan2(distY, distX);
            //System.out.println("\nAngle: " + angle);
            //Figuring out triangular stuff for the first link - pointing to the transition node
            double hypotenuse1 = length / 3.0;
            double opposite1 = Math.sin(angle) * hypotenuse1;
            double adjacent1 = Math.cos(angle) * hypotenuse1;

            //this.nodes.add(link);

            //Now we set it:
            link.x = coords[0] + adjacent1;
            link.y = coords[1] + opposite1;

            //System.out.println("\nlink.x: " + link.x + "\nlink.y: " + link.y);
        }
        else{

            link.x = link.first.x + 180;
            link.y = link.first.y;



        }

        notifySubscribers();
    }

    /**
     * Moves a node
     */
    public void moveNode(SMItem node, double dx, double dy){

        node.move(dx, dy);
        notifySubscribers();

    }

    /**
     * Removes a node (deletes it)
     */
    public void removeNode(SMStateNode node) {
        //try {

        //We need a temporary holder for the to-be-removed links,
        //Otherwise we will cause a concurrency exception/race condition:
        ArrayList<SMTransitionLink> tempLinks = new ArrayList<>();

        this.links.forEach(l->{
            System.out.println("iteration");
            if(l.first.equals(node) || l.second.equals(node)){
                System.out.println("Inside If Statement");
                tempLinks.add(l);
            }
        });

        tempLinks.forEach(l->{
            removeLink(l);
        });/*
       System.out.println("Size of links array: " + this.links.size());
            int tempSize = this.links.size();
            for (int i=0; i<tempSize; i++) {
                System.out.println("checked a link, i= " + i);
                if (this.links.get(i).first.equals(node) || this.links.get(i).second.equals(node)) {
                    removeLink(this.links.get(i));
                    i=0;
                }
                System.out.println("Tempsize: " + tempSize);
            }*/
       // }
       // catch(ConcurrentModificationException e){}
        this.nodes.remove(node);
        notifySubscribers();

    }

    public double[] bestSelfLink(SMStateNode node){
        double[] output = {0, 0};



        return output;
    }
}
