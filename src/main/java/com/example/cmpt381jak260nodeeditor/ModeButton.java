package com.example.cmpt381jak260nodeeditor;

import javafx.scene.control.Button;

public class ModeButton extends Button {

    Tools tool;

    public ModeButton(Tools tool){


        super();
        this.tool = tool;
        this.setPrefSize(50, 50);
        this.setStyle("-fx-background-color: turquoise");



    }

    public void select(){

        this.setStyle("-fx-background-color: red");
        System.out.println("selected");
        this.setWidth(70);
        this.setHeight(70);

    }

    public void unselect(){

        this.setStyle("-fx-background-color: turquoise");
        System.out.println("unselected");
        this.setWidth(50);
        this.setHeight(50);

    }

}
