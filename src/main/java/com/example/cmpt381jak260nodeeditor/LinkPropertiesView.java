package com.example.cmpt381jak260nodeeditor;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LinkPropertiesView extends StackPane {


    public LinkPropertiesView(){

        //working our way top to bottom of the template in the assignment description:
        VBox vbox = new VBox();

        Label menulabel = new Label("Transition");
        menulabel.setFont(new Font(30));
        //label.setTextAlignment();

        HBox labelBox = new HBox(menulabel);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setStyle("-fx-background-color: gray");

        Label eventLabel = new Label("Event:");

        TextField eventInsert = new TextField();
        eventInsert.setPromptText("No Event");

        Label contextLabel = new Label("Context:");
        TextField contextInsert = new TextField();
        contextInsert.setPromptText("No Context");
        contextInsert.setPrefHeight(100);


        Label seLabel = new Label("Side Effects:");
        TextField seInsert = new TextField();
        seInsert.setPromptText("No Side Effects");

        seInsert.setPrefHeight(100);

        Button updateButton = new Button("Update");





        vbox.getChildren().addAll(labelBox, eventLabel, eventInsert,
                contextLabel, contextInsert, seLabel, seInsert, updateButton);

        vbox.setPadding(new Insets(0, 10, 0, 10));
        this.setPrefWidth(250);

        this.getChildren().addAll(vbox);
    }

}
