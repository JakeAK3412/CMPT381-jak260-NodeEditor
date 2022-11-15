package com.example.cmpt381jak260nodeeditor;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NodeController {

    InteractionModel iModel;
    SMModel model;

    double prevX, prevY;
    double dX, dY;

    //So we know which we're drawing a link from
    SMStateNode prevNode;

    SMTransitionLink prevLink;

    public void handleDelete() {
        SMItem node = this.iModel.getSelectedNode();

        //System.out.println("Get Deleted");

        //Two different cases, if it's a transition node or a state node
        if(node != null){
            if(node.isTransition()){
                this.model.removeLink((SMTransitionLink) node);
            }
            else{
                this.model.removeNode((SMStateNode) node);
            }
        }
    }


    enum State {READY,PREPARE_CREATE, DRAGGING}
    State currentState = State.READY;

    //For changing views
    //Stage stage;
    NodePropertiesView nodeView;
    LinkPropertiesView linkView;

    public NodeController(LinkPropertiesView linkView, NodePropertiesView nodeView){

        //this.stage = stage;

        this.nodeView = nodeView;
        this.linkView = linkView;
    }

    public void handleUpdate(ActionEvent actionEvent) {

            SMTransitionLink node = (SMTransitionLink) iModel.getSelectedNode();

            if(node!=null){


                model.setEventName(linkView.eventInsert.getText(), node);
                model.setSideEffectName(linkView.seInsert.getText(), node);
                model.setContextName(linkView.contextInsert.getText(), node);

                linkView.eventInsert.clear();
                linkView.seInsert.clear();
                linkView.contextInsert.clear();
            }

    }

    //If the enter key is pressed
    public void handleKey() {

        SMStateNode node = ((SMStateNode) iModel.getSelectedNode());

        if(node != null) {
            model.setStateName(this.nodeView.stateInsert.getText(), node);
            this.nodeView.stateInsert.clear();

        }
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
                    if (this.model.hitNode(event.getX() + iModel.scrollX, event.getY()+ iModel.scrollY)) {
                        System.out.println("Yes");
                        this.iModel.setSelectedNode(this.model.whichNode(event.getX()+ iModel.scrollX, event.getY() + iModel.scrollY));
                        this.prevX = event.getX() + iModel.scrollX;
                        this.prevY = event.getY() + iModel.scrollY;
                        currentState = State.DRAGGING;

                        //See which view we have to show:
                        if(this.model.whichNode(event.getX() + iModel.scrollX, event.getY()+ iModel.scrollY).isTransition()){

                            this.nodeView.setManaged(false);
                            this.nodeView.setVisible(false);

                            this.linkView.setManaged(true);
                            this.linkView.setVisible(true);
                        }
                        else{
                            this.nodeView.setManaged(true);
                            this.nodeView.setVisible(true);

                            this.linkView.setManaged(false);
                            this.linkView.setVisible(false);
                        }


                    } else {

                        this.model.addNode(event.getX() + iModel.scrollX, event.getY() + iModel.scrollY);

                    }
                }
            }
        }
        else if(this.iModel.getSelectedTool() == Tools.LINK){

            switch (currentState) {
                case READY -> {//If it's a hit and it's not a transition node
                    if (this.model.hitNode(event.getX() + iModel.scrollX, event.getY()+ iModel.scrollY)
                            && !(this.model.whichNode(event.getX()+ iModel.scrollX, event.getY()+ iModel.scrollY).isTransition())){
                        this.prevNode = (SMStateNode) this.model.whichNode(event.getX()+ iModel.scrollX, event.getY()+ iModel.scrollY);
                        this.iModel.setSelectedNode(this.model.whichNode(event.getX()+ iModel.scrollX, event.getY()+ iModel.scrollY));
                        this.prevX = event.getX()+ iModel.scrollX;
                        this.prevY = event.getY()+ iModel.scrollY;
                        //this.model.removeLink(this.prevLink);
                        this.prevLink = this.model.addLink(this.prevX, this.prevY, event.getX()+ iModel.scrollX, event.getY()+ iModel.scrollY);

                        currentState = State.DRAGGING;

                    }
                }
            }

        }
        else{
            //Now we move the world
            System.out.println("Move tool pressed");
            switch (currentState){
                case READY -> {
                    this.prevX = event.getX()+ iModel.scrollX;
                    this.prevY = event.getY()+ iModel.scrollY;
                }
            }
        }
    }

    public void handleDragged(MouseEvent event) {
        if(this.iModel.getSelectedTool() == Tools.CURSOR) {
            switch (currentState) {
                case PREPARE_CREATE -> {
                    currentState = State.READY;
                }
                case DRAGGING -> {
                    dX = event.getX() + iModel.scrollX - prevX;
                    dY = event.getY() + iModel.scrollY - prevY;
                    prevX = event.getX() + iModel.scrollX;
                    prevY = event.getY() + iModel.scrollY;
                    model.moveNode( iModel.getSelectedNode(), dX, dY);

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
                    this.prevLink = this.model.addLink(this.prevX- iModel.scrollX, this.prevY- iModel.scrollY,
                            event.getX(), event.getY());


                }
            }

        }
        else{
            //System.out.println("Got to pan switch");
            switch(currentState){

                case PREPARE_CREATE -> {
                    //System.out.println("prepare create in pan switch");
                    currentState = State.READY;
                }
                case DRAGGING -> {

                }

                case READY -> {
                    //Pan the screen by the amount of space we've moved:
                    this.iModel.panRegion(event.getX()-prevX, event.getY()-prevY);

                    System.out.println("\nprevX: " + prevX + "\n" +
                            "prevY: " + prevY + "\neventX: " + event.getX() +
                            "\neventY: " + event.getY());
                    prevX = event.getX();
                    prevY = event.getY();
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
                    if(this.model.hitNode(event.getX() + iModel.scrollX, event.getY()+ iModel.scrollY)) {
                        //System.out.println("hit");
                        //model.addLink(event.getX(), event.getY());
                        this.model.removeLink(this.prevLink);

                        double[] coords;
                        //Here basically we draw the nice-looking final link that just goes border to border
                        if(this.model.whichNode(event.getX()+
                                iModel.scrollX, event.getY()+ iModel.scrollY) instanceof SMStateNode) {
                             coords = this.model.bestLink(this.prevNode, (SMStateNode) this.model.whichNode(event.getX() +
                                    iModel.scrollX, event.getY() + iModel.scrollY));


                        this.prevLink = model.addLink(coords[0], coords[1], coords[2], coords[3]);
                       //Set this link's flag to true and the first and second nodes to the right ones
                        this.prevLink.second = (SMStateNode) this.model.whichNode(event.getX()+ iModel.scrollX, event.getY()+ iModel.scrollY);
                        this.prevLink.first = this.prevNode;

                        //Made a method that notifies subscribers so the arrow is drawn on the final
                        //link
                        model.makeFinalLink(this.prevLink);

                        currentState = State.READY;
                        this.prevLink = null;
                    }
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
