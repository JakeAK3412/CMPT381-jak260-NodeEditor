package com.example.cmpt381jak260nodeeditor;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

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

    private void drawArrow(GraphicsContext gc, double x1, double y1, double x2, double y2){

        gc.setFill(Color.BLACK);

        double dx = x2 - x1, dy = y2 - y1;
        //Get the angle of the arrow (where it's pointing)
        double arrowAngle = Math.atan2(dy, dx);

        int length = (int) Math.sqrt(dx * dx + dy * dy);

       Transform arrowTransform = Transform.translate(x1, y1);

        arrowTransform = arrowTransform.createConcatenation(Transform.rotate(Math.toDegrees(arrowAngle), 0, 0));

        gc.setTransform(new Affine(arrowTransform));

        //Draw the triangle
        gc.fillPolygon(new double[]{length, length - 8, length - 8, length}, new double[]{0, -8, 8, 0},
                4);

        //Draw the link
        gc.strokeLine(0, 0, length, 0);

        //Now we reset the graphics context's transform so it doesn't mess up future drawings
        gc.setTransform(new Affine(Transform.translate(0, 0)));

        //drawShapes(gc);
/*
        int firstX = 90;
        int secondX = 190;
        int thirdX = 10;

        int firstY =30;
        int secondY =170;
        int thirdY =170;

        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.fillPolygon(new double[]{firstX, secondX,thirdX},
                new double[]{firstY, secondY, thirdY}, 3);

        gc.strokePolygon(new double[]{firstX, secondX,thirdX},
                new double[]{firstY, secondY, thirdY}, 3);

        gc.strokeLine(x1, y1, x2, y2);


        gc.setFill(Color.BLACK);
        double dx = x2-x1;
        double dy = y2-y1;

        double angle = Math.atan2(dy, dx);
        double length = Math.sqrt(dx*dx + dy*dy);

        gc.strokeLine(0, 0, length, 0);

        gc.fillPolygon(new double[]{length, length - 2, length-2, length, length}, new double[]{0, length, length}, 3);
        gc.strokePolygon(new double[]{length, length - 2, length-2, length, length}, new double[]{0, length, length}, 3);
*/

    }

    private void drawTransition(GraphicsContext gc, double x1, double y1, double x2, double y2){
        drawArrow(gc, x1, y1, x2, y2);
    }

    private void drawLink(){
        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        this.model.getLinks().forEach(l->{
            gc.setFill(Color.BLACK);

            if(l.isFinal){
                //If it's a final one and we need to handle moving nodes it's connected to
                double[] coords = this.model.bestLink(l.first, l.second);
                drawTransition(gc, coords[0], coords[1], coords[2], coords[3]);


            }
            //If it's a temporary link
            else {
                gc.strokeLine(l.startX, l.startY, l.endX, l.endY);
            }
        });
    }

    private void drawNode(){
        //I made the rectangle get drawn with the centre of the rectangle
        //where the mouse is clicked - for user's ease of use
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        this.model.getNodes().forEach(n->{

            if(n.equals(iModel.getSelectedNode())) {
                //Make border red for selected
                gc.setFill(Color.YELLOW);
                gc.fillRect(n.x - 40, n.y - 30, 80, 60);


                gc.setFill(Color.RED);
                gc.fillText("Default", n.x-20, n.y);


                gc.setStroke(Color.RED);
                gc.strokeRect(n.x - 40, n.y - 30, 80, 60);
            }
            else{

                gc.setFill(Color.YELLOW);
                gc.fillRect(n.x - 40, n.y - 30, 80, 60);
                gc.setStroke(Color.BLACK);

                gc.setFill(Color.BLACK);
                gc.fillText("Default", n.x-20, n.y);
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
        drawLink();
        this.canvas.setCursor(iModel.getCursor());


    }

    public void modelChanged(){

        drawNode();
        drawLink();

    }

    public void setController(NodeController controller){

        this.canvas.setOnMousePressed(controller::handlePressed);
        this.canvas.setOnMouseDragged(controller::handleDragged);
        this.canvas.setOnMouseReleased(controller::handleReleased);


    }




}
