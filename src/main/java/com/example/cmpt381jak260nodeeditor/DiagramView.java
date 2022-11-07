package com.example.cmpt381jak260nodeeditor;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DiagramView extends StackPane implements IModelListener, SMModelListener{

    Canvas canvas;
    GraphicsContext gc;

    InteractionModel iModel;
    SMModel model;
    public DiagramView() {
        this.canvas = new Canvas(800, 800);
        this.gc = this.canvas.getGraphicsContext2D();
        //this.gc.setFill(Color.AQUAMARINE);
        //this.gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

        this.setStyle("-fx-background-color: lightblue");


        this.getChildren().add(this.canvas);
    }

    private void drawNode(){
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        this.model.getNodes().forEach(n->{

            if(n.equals(iModel.getSelectedNode())) {
                gc.setFill(Color.YELLOW);
                gc.fillRect(n.x - 40, n.y - 30, 80, 60);
                gc.setStroke(Color.RED);
                gc.strokeRect(n.x - 40, n.y - 30, 80, 60);
            }
            else{

                gc.setFill(Color.YELLOW);
                gc.fillRect(n.x - 40, n.y - 30, 80, 60);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(n.x - 40, n.y - 30, 80, 60);


            }


        });



    }

    public void setIModel(InteractionModel iModel){
        this.iModel = iModel;
    }

    public void setModel(SMModel model){
        this.model = model;
    }

    public void iModelChanged(){

        drawNode();
        this.canvas.setCursor(iModel.getCursor());


    }

    public void modelChanged(){

        drawNode();

    }

    public void setController(NodeController controller){

        this.canvas.setOnMousePressed(controller::handlePressed);
        this.canvas.setOnMouseDragged(controller::handleDragged);
        this.canvas.setOnMouseReleased(controller::handleReleased);


    }




}
