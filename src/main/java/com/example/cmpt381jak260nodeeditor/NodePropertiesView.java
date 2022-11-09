package com.example.cmpt381jak260nodeeditor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class NodePropertiesView extends StackPane {

    public NodePropertiesView(){
        //working our way top to bottom of the template in the assignment description:
        VBox vbox = new VBox();

        Label menulabel = new Label("State");
        menulabel.setFont(new Font(30));
        //label.setTextAlignment();

        HBox labelBox = new HBox(menulabel);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setStyle("-fx-background-color: gray");

        Label stateLabel = new Label("State Name:");

        TextField stateInsert = new TextField();
        stateInsert.setPromptText("Default");



        vbox.getChildren().addAll(labelBox, stateLabel, stateInsert);

        vbox.setPadding(new Insets(0, 10, 0, 10));
        this.setPrefWidth(250);

        this.getChildren().addAll(vbox);
    }
}
