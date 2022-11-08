package com.example.cmpt381jak260nodeeditor;

import javafx.scene.input.MouseEvent;

public class NodeController {

    InteractionModel iModel;
    SMModel model;

    double prevX, prevY;
    double dX, dY;

    //So we know which we're drawing a link from
    SMStateNode prevNode;

    SMTransitionLink prevLink;

    enum State {READY,PREPARE_CREATE, DRAGGING}
    State currentState = State.READY;

    public NodeController(){

    }

    public void setIModel(InteractionModel iModel){
        this.iModel = iModel;
    }
    public void setModel(SMModel model){
        this.model = model;
    }

    public void handleToolChanged(Tools tool){
        this.iModel.setTool(tool);
    }

    public void handlePressed(MouseEvent event){
        if(this.iModel.getSelectedTool() == Tools.CURSOR){
            //System.out.println("Cursor tool pressed");
            switch (currentState) {
                case READY -> {
                    if (this.model.hitNode(event.getX(), event.getY())) {

                        this.iModel.setSelectedNode(this.model.whichNode(event.getX(), event.getY()));
                        this.prevX = event.getX();
                        this.prevY = event.getY();
                        currentState = State.DRAGGING;
                    } else {

                        this.model.addNode(event.getX(), event.getY());

                    }
                }
            }
        }
        else if(this.iModel.getSelectedTool() == Tools.LINK){

            switch (currentState) {
                case READY -> {
                    if (this.model.hitNode(event.getX(), event.getY())) {
                        this.prevNode = this.model.whichNode(event.getX(), event.getY());
                        this.iModel.setSelectedNode(this.model.whichNode(event.getX(), event.getY()));
                        this.prevX = event.getX();
                        this.prevY = event.getY();
                        //this.model.removeLink(this.prevLink);
                        this.prevLink = this.model.addLink(this.prevX, this.prevY, event.getX(), event.getY());

                        currentState = State.DRAGGING;

                    }
                }
            }

        }
        else{
            System.out.println("Move tool pressed");
        }
    }

    public void handleDragged(MouseEvent event) {
        if(this.iModel.getSelectedTool() == Tools.CURSOR) {
            switch (currentState) {
                case PREPARE_CREATE -> {
                    currentState = State.READY;
                }
                case DRAGGING -> {
                    dX = event.getX() - prevX;
                    dY = event.getY() - prevY;
                    prevX = event.getX();
                    prevY = event.getY();
                    model.moveNode(iModel.getSelectedNode(), dX, dY);

                }
            }
        }
        else if (this.iModel.getSelectedTool() == Tools.LINK){

            switch (currentState) {
                case PREPARE_CREATE -> {
                    currentState = State.READY;
                }
                case DRAGGING -> {
                    /*dX = event.getX() - prevX;
                    dY = event.getY() - prevY;
                    prevX = event.getX();
                    prevY = event.getY();*/
                    this.model.removeLink(this.prevLink);
                    this.prevLink = this.model.addLink(this.prevX, this.prevY, event.getX(), event.getY());


                }
            }

        }
    }

    public void handleReleased(MouseEvent event) {
        if(this.iModel.getSelectedTool() == Tools.CURSOR) {
            switch (currentState) {
                case PREPARE_CREATE -> {
                    model.addNode(event.getX(), event.getY());
                    currentState = State.READY;
                }
                case DRAGGING -> {
                    //iModel.unselectNode();
                    currentState = State.READY;
                }
            }
        }
        else if(this.iModel.getSelectedTool() == Tools.LINK){

            switch (currentState) {
                case PREPARE_CREATE -> {
                    currentState = State.READY;
                }
                case DRAGGING -> {
                    //iModel.unselectNode();
                    //System.out.println("got to pc");
                    if(this.model.hitNode(event.getX(), event.getY())) {
                        //System.out.println("hit");
                        //model.addLink(event.getX(), event.getY());
                        this.model.removeLink(this.prevLink);

                        //Here basically we draw the nice-looking final link that just goes border to border
                        double[] coords = this.model.bestLink(this.prevNode, this.model.whichNode(event.getX(), event.getY()));

                        this.prevLink = model.addLink(coords[0], coords[1], coords[2], coords[3]);
                       //Set this link's flag to true and the first and second nodes to the right ones
                        this.prevLink.second = this.model.whichNode(event.getX(), event.getY());
                        this.prevLink.first = this.prevNode;

                        //Made a method that notifies subscribers so the arrow is drawn on the final
                        //link
                        model.makeFinalLink(this.prevLink);

                        currentState = State.READY;
                        this.prevLink = null;

                    }
                    else{
                        //System.out.println("missed");
                        //If the link doesn't connect to another node
                        this.model.removeLink(this.prevLink);
                        currentState = State.READY;
                        this.prevLink = null;

                    }

                }
            }

        }
    }


}
