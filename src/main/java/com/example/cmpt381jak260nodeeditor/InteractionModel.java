package com.example.cmpt381jak260nodeeditor;

import javafx.scene.Cursor;

import java.util.ArrayList;

public class InteractionModel {

    private ArrayList<IModelListener> subscribers;
    public Tools selectedTool;
    public Cursor cursor;

    public SMItem selectedNode;

    public InteractionModel(){
        this.subscribers = new ArrayList<>();
    }

    public void setTool(Tools tool){
        this.selectedTool = tool;
        if(tool == Tools.CURSOR){
        this.setCursor(Cursor.DEFAULT);}

        else if(tool == Tools.LINK){
            this.setCursor(Cursor.CROSSHAIR);
        }

        else{
            this.setCursor(Cursor.HAND);
        }

        notifySubscribers();
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.iModelChanged());
    }

    public void setSelectedNode(SMItem node){
        this.selectedNode = node;
        notifySubscribers();
    }

    public SMItem getSelectedNode(){
        return this.selectedNode;
    }

    public void setCursor(Cursor cursor){
        this.cursor = cursor;
        //notifySubscribers();
    }

    public Tools getSelectedTool() {
        return selectedTool;
    }

    public Cursor getCursor(){
        return this.cursor;
    }

    public void setSelectedTool(Tools selectedTool) {
        this.selectedTool = selectedTool;
        notifySubscribers();
    }

    public void addSubscriber(IModelListener sub) { // for version 2
        subscribers.add(sub);
    }

    public void unselectNode(){
        this.selectedNode = null;
        notifySubscribers();
    }




}
