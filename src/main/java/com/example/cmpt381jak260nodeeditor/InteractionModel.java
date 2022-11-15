package com.example.cmpt381jak260nodeeditor;

import javafx.scene.Cursor;

import java.util.ArrayList;

public class InteractionModel {

    private ArrayList<IModelListener> subscribers;
    public Tools selectedTool;
    public Cursor cursor;

    public SMItem selectedNode;

    public double worldWidth, worldHeight;
    public double viewWidth, viewHeight;

    public double scrollX, scrollY;

    public InteractionModel(){

        this.subscribers = new ArrayList<>();
        this.scrollX = 0;
        this.scrollY = 0;
    }





    public void panRegion(double dx, double dy){
        //Set x
        this.scrollX -= dx;
        //Check if it's out of bounds:
        if (scrollX < 0) scrollX = 0;
        if (scrollX > worldWidth - viewWidth) scrollX = worldWidth - viewWidth;

        //Set y:
        this.scrollY -= dy;
        //Check if out of bounds again:
        if(scrollY <0) scrollY = 0;
        if(scrollY>worldHeight-viewHeight)scrollY = worldHeight-viewHeight;

        System.out.println("\nScrollX: " + scrollX + "\nScrollY: "+ scrollY);

        notifySubscribers();


    }

    public double getWorldWidth() {
        return worldWidth;
    }

    public void setWorldWidth(double worldWidth) {
        this.worldWidth = worldWidth;
    }

    public double getWorldHeight() {
        return worldHeight;
    }

    public void setWorldHeight(double worldHeight) {
        this.worldHeight = worldHeight;
    }

    public double getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(double viewWidth) {
        this.viewWidth = viewWidth;
    }

    public double getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(double viewHeight) {
        this.viewHeight = viewHeight;
    }

    public double getScrollX() {
        return scrollX;
    }

    public void setScrollX(double scrollX) {
        this.scrollX = scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }

    public void setScrollY(double scrollY) {
        this.scrollY = scrollY;
    }

    public void setTool(Tools tool){
        this.selectedTool = tool;
        if(tool == Tools.CURSOR){
        this.setCursor(Cursor.DEFAULT);}

        else if(tool == Tools.LINK){
            this.setCursor(Cursor.CROSSHAIR);
        }

        else{
            this.setCursor(Cursor.MOVE);
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
