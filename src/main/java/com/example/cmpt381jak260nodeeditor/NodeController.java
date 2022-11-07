package com.example.cmpt381jak260nodeeditor;

public class NodeController {

    InteractionModel iModel;

    public NodeController(){

    }

    public void setIModel(InteractionModel iModel){
        this.iModel = iModel;
    }

    public void handleToolChanged(Tools tool){
        this.iModel.setTool(tool);
    }
}
