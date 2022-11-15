package com.example.cmpt381jak260nodeeditor;


import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


    TextField eventInsert;
    TextField contextInsert;
    TextField seInsert;
    Button updateButton;
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

        this.eventInsert = new TextField();
        eventInsert.setPromptText("No Event");



        Label contextLabel = new Label("Context:");
        this.contextInsert = new TextField();
        contextInsert.setPromptText("No Context");

        contextInsert.setPrefHeight(150);
        contextInsert.setAlignment(Pos.TOP_LEFT);

        Label seLabel = new Label("Side Effects:");
        this.seInsert = new TextField();
        seInsert.setPromptText("No Side Effects");
        seInsert.setPrefHeight(150);
        seInsert.setAlignment(Pos.TOP_LEFT);



        this.updateButton = new Button("Update");





        vbox.getChildren().addAll(labelBox, eventLabel, eventInsert,
                contextLabel, contextInsert, seLabel, seInsert, updateButton);

        vbox.setPadding(new Insets(0, 10, 0, 10));
        //this.setPrefWidth(250);
        this.setMaxWidth(250);
        this.setMinWidth(250);

        this.getChildren().addAll(vbox);
    }


    public void setController(NodeController controller){

        this.updateButton.setOnAction(controller::handleUpdate);

    }

}
