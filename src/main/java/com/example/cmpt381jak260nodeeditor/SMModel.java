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
        return false;
    }

    public SMStateNode whichNode(double x, double y){
        for(SMStateNode n: this.nodes){
            if(n.contains(x, y)) return n;
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
        notifySubscribers();
    }

    public void moveNode(SMStateNode node, double dx, double dy){

        node.move(dx, dy);
        notifySubscribers();

    }
}
