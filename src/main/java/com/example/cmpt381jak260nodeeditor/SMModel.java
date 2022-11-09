package com.example.cmpt381jak260nodeeditor;

import java.util.ArrayList;

public class SMModel {

    private ArrayList<SMModelListener> subscribers;
    private ArrayList<SMStateNode> nodes;
    private ArrayList<SMTransitionLink> links;

    public SMModel(){
        this.subscribers = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.links = new ArrayList<>();
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
            System.out.println("checked a link");
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

    public void makeFinalLink(SMTransitionLink link){
        link.isFinal = true;

        double[] coords = this.bestLink(link.first, link.second);
        System.out.println(coords[0]);

        //Set the coords of the transition node, doing some trig:


        double distX = link.endX-link.startX;
        double distY = link.endY-link.startY;
        //Get the length
        //System.out.println("\nPoint 1: " + x1  +", " + y1 + "\nPoint 2: " + x2 + ", " + y2);
        double length = Math.sqrt(distX * distX  + distY * distY);
        //System.out.println(length);
        //Get the angle
        double angle = Math.atan2(distY, distX);
        //System.out.println("\nAngle: " + angle);
        //Figuring out triangular stuff for the first link - pointing to the transition node
        double hypotenuse1 = length/3.0;
        double opposite1 = Math.sin(angle) * hypotenuse1;
        double adjacent1 = Math.cos(angle)* hypotenuse1;

        //this.nodes.add(link);

        //Now we set it:
        link.x = coords[0] + adjacent1;
        link.y = coords[1] + opposite1;

        System.out.println("\nlink.x: " + link.x + "\nlink.y: " + link.y);


        notifySubscribers();
    }

    public void moveNode(SMItem node, double dx, double dy){

        node.move(dx, dy);
        notifySubscribers();

    }
}
