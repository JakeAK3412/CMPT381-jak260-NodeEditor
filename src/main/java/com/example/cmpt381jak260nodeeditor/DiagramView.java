package com.example.cmpt381jak260nodeeditor;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DiagramView extends StackPane implements IModelListener{

    Canvas canvas;
    GraphicsContext gc;

    InteractionModel iModel;
    public DiagramView() {
        this.canvas = new Canvas(800, 800);
        this.gc = this.canvas.getGraphicsContext2D();
        //this.gc.setFill(Color.AQUAMARINE);
        //this.gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

        this.setStyle("-fx-background-color: lightblue");


        this.getChildren().add(this.canvas);
    }

    public void setIModel(InteractionModel iModel){
        this.iModel = iModel;
    }

    public void iModelChanged(){


        this.canvas.setCursor(iModel.getCursor());





    }




}
