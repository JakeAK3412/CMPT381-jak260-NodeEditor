package com.example.cmpt381jak260nodeeditor;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ModeButton extends Button {

    //For comparisons
    Tools tool;

    //For selecting:

    Background selected;
    Background unselected;

    public ModeButton(Tools tool, String pathToIMG){


        super();


        this.tool = tool;
        this.setPrefSize(70, 70);
        this.setMinSize(50, 50);
        //For the selection sizing
        //this.setPadding(new Insets(0, 5, 0, 5));
        this.setStyle("-fx-background-color: turquoise; -fx-padding: 20");


        //Read in the image icon
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

        //this.selected = new Background(new BackgroundFill(Color.TURQUOISE, new CornerRadii(10),new Insets(5, 5, 5, 5)));
        //this.unselected = new Background(new BackgroundFill(Color.TURQUOISE, new CornerRadii(10),null));
        this.setGraphic(imgView);

        //this.setPrefSize(80, 80);

        //this.setBackground(unselected);



        //this.hbox = new HBox();
        //this.hbox.setPrefWidth(70);

        //this.hbox.setPadding(new Insets(0, 5, 0, 5));
        //this.setMaxWidth(Double.MAX_VALUE);

        



    }

    public void select(){

        this.setStyle("-fx-background-color: blue; -fx-padding: 0");
        //this.setBackground(selected);
        //System.out.println("selected");
        //this.hbox.setPadding(new Insets(0, 5, 0, 5));
        //this.setWidth(70);
        //this.setHeight(70);

    }

    public void unselect(){

        //this.hbox.setPadding(new Insets(0, 0, 0, 0));
        this.setStyle("-fx-background-color: turquoise; -fx-padding: 10");
        //this.setBackground(unselected);
       // System.out.println("unselected");
        //this.setWidth(50);
        //this.setHeight(50);

    }

}
