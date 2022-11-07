package com.example.cmpt381jak260nodeeditor;

import java.util.ArrayList;

public class InteractionModel {

    private ArrayList<IModelListener> subscribers;
    public Tools selectedTool;

    public InteractionModel(){
        this.subscribers = new ArrayList<>();
    }

    public void setTool(Tools tool){
        this.selectedTool = tool;
        notifySubscribers();
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.iModelChanged());
    }

    public Tools getSelectedTool() {
        return selectedTool;
    }

    public void setSelectedTool(Tools selectedTool) {
        this.selectedTool = selectedTool;
        notifySubscribers();
    }

    public void addSubscriber(IModelListener sub) { // for version 2
        subscribers.add(sub);
    }




}
