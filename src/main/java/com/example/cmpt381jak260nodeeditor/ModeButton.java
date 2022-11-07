package com.example.cmpt381jak260nodeeditor;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ModeButton extends Button {

    Tools tool;

    public ModeButton(Tools tool, String pathToIMG){


        super();
        this.tool = tool;
        this.setPrefSize(50, 50);
        this.setStyle("-fx-background-color: turquoise");

        InputStream stream = null;
        try {
            stream = new FileInputStream(pathToIMG);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image icon = new Image(stream);

        ImageView imgView = new ImageView(icon);
        imgView.setFitHeight(50);
        imgView.setFitWidth(50);
        imgView.setPreserveRatio(true);

        this.setGraphic(imgView);



    }

    public void select(){

        this.setStyle("-fx-background-color: red");
        //System.out.println("selected");
        this.setWidth(70);
        this.setHeight(70);

    }

    public void unselect(){

        this.setStyle("-fx-background-color: turquoise");
       // System.out.println("unselected");
        this.setWidth(50);
        this.setHeight(50);

    }

}
