package com.example.cmpt381jak260nodeeditor;

import javafx.scene.input.MouseEvent;

public class NodeController {

    InteractionModel iModel;
    SMModel model;

    double prevX, prevY;
    double dX, dY;

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
            System.out.println("Link pressed");
        }
        else{
            System.out.println("Move tool pressed");
        }
    }

    public void handleDragged(MouseEvent event) {
        switch (currentState) {
            case PREPARE_CREATE -> {
                currentState = State.READY;
            }
            case DRAGGING -> {
                dX = event.getX() - prevX;
                dY = event.getY() - prevY;
                prevX = event.getX();
                prevY = event.getY();
                model.moveNode(iModel.getSelectedNode(), dX,dY);
            }
        }
    }

    public void handleReleased(MouseEvent event) {
        switch (currentState) {
            case PREPARE_CREATE -> {
                model.addNode(event.getX(),event.getY());
                currentState = State.READY;
            }
            case DRAGGING -> {
                //iModel.unselectNode();
                currentState = State.READY;
            }
        }
    }


}
