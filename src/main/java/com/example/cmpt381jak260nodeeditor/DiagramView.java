package com.example.cmpt381jak260nodeeditor;

import javafx.beans.Observable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

public class DiagramView extends StackPane implements IModelListener, SMModelListener{

    Canvas canvas;
    GraphicsContext gc;

    //For part 4, the viewport panning and resizing
    double viewWidth, viewHeight;
    InteractionModel iModel;
    SMModel model;

    Pane root;

    public DiagramView(double viewWidth, double viewHeight) {

        this.viewHeight = viewHeight;
        this.viewWidth = viewWidth;
        this.canvas = new Canvas(viewWidth, viewHeight);

        //This makes the canvas grab the keyboard focus when it's clicked,
        //So we can delete things with a key press

        this.canvas.setFocusTraversable(true);
        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus());


        this.gc = this.canvas.getGraphicsContext2D();


        //this.gc.setFill(Color.AQUAMARINE);
        //this.gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

        this.setStyle("-fx-background-color: lightblue");


        this.getChildren().add(this.canvas);



        this.widthProperty().addListener((observable, oldVal, newVal) -> canvas.setWidth(newVal.doubleValue()));
        this.heightProperty().addListener((observable, oldVal, newVal) -> canvas.setHeight(newVal.doubleValue()));


    }
    private void drawOverlayLinks(){

        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //gc.clearRect(0,0,viewWidth,viewHeight);
        this.model.getLinks().forEach(l->{
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.BLACK);

            if(l.isFinal){
                //If it's a final one and we need to handle moving nodes it's connected to
                double[] coords = this.model.bestLink(l.first, l.second);

                /*Modify them for the scrolling:
                coords[0] -= iModel.scrollX;
                coords[1] -= iModel.scrollY;
                coords[2] -= iModel.scrollX;
                coords[3] -= iModel.scrollY;*/

                //Check if the transition node is selected:
                if(l.equals(this.iModel.getSelectedNode())) {
                    //If it's a self transition:
                    if(l.first.equals(l.second)){
                        drawSelfTransition(gc, l, true, true);
                    }
                    else {
                        drawTransition(gc, l, coords, true, true);
                    }
                }
                else{
                    //If it's a self transition:
                    if(l.first.equals(l.second)){
                        drawSelfTransition(gc, l, false, true);
                    }
                    else {
                        drawTransition(gc, l, coords, false, true);
                    }

                }


            }
            //If it's a temporary link - if we're drawing links, we aren't panning,
            //So no need to adjust for scroll values
            else {
                gc.strokeLine(l.startX, l.startY, l.endX, l.endY);
            }
        });

    }

    private void drawOverlayNodes(){
        this.model.getNodes().forEach(n->{

            if(n.equals(iModel.getSelectedNode())) {
                //Make border red for selected
                gc.setFill(Color.YELLOW);
                gc.fillRect(n.x - 40, n.y - 30, 80, 60);


                gc.setFill(Color.RED);
                gc.fillText(n.getName(), n.x-40, n.y);


                gc.setStroke(Color.RED);
                gc.strokeRect(n.x - 40, n.y - 30, 80, 60);
            }
            else{

                gc.setFill(Color.YELLOW);
                gc.fillRect(n.x - 40, n.y - 30, 80, 60);
                gc.setStroke(Color.BLACK);

                gc.setFill(Color.BLACK);
                gc.fillText(n.getName(), n.x-40, n.y);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(n.x - 40 , n.y - 30, 80, 60);


            }


        });

    }
    public void drawOverlayRect(){
        //System.out.println("overlay rect");
        double width = canvas.getWidth() / iModel.viewWidth;
        double height = canvas.getHeight() / iModel.viewHeight;
        gc.setFill(Color.rgb(0, 0, 0, 0.1));
        double minimum = Math.min(width, height);

        gc.fillRect(0+iModel.scrollX, 0+iModel.scrollY,
                iModel.scrollX+800, iModel.scrollY+800);

    }
    private void drawOverlay(){

        //drawOverlayRect();
        //gc.save();
        gc.setGlobalAlpha(0.5);
        Scale scale = new Scale();
        //Scale scaleY = new Scale();

        scale.setX(0.5);
        scale.setY(0.5);

        //gc.save();
        gc.setTransform(new Affine(scale));
       // gc.getTransform().append(scaleY);

        drawOverlayRect();
        drawOverlayNodes();
        drawOverlayLinks();

        //gc.restore();

        gc.setTransform(new Affine());

        gc.setGlobalAlpha(1);


       // gc.getTransform().
    }

    public void setCanvasWidth(Observable observable, Number oldVal, Number newVal){
        System.out.println("hit the resizing method width");

        System.out.println("oldVal: " + oldVal + "\nnewVal: " + newVal);

        viewWidth = newVal.doubleValue()-330;
        iModel.setViewWidth(viewWidth);








    }
    public void setCanvasHeight(Observable observable, Number oldVal, Number newVal){
        System.out.println("hit the resizing method height");

        System.out.println("oldVal: " + oldVal + "\nnewVal: " + newVal);

        viewHeight = newVal.doubleValue();
        iModel.setViewHeight(viewHeight);




    }

    private void drawSelfTransition(GraphicsContext gc, SMTransitionLink link, boolean isSelected
    , boolean isScaled){


        if(isScaled) {
            gc.setStroke(Color.BLACK);
            //If it's selected
            if (isSelected) {
                gc.setStroke(Color.RED);
            }
            //Node:
            gc.setFill(Color.WHITESMOKE);
            gc.fillRect(link.first.x + 140,
                    link.first.y - 60, 120, 120);


            gc.setFill(Color.BLACK);
            gc.fillText(" -Event:\n " + link.getEvent() + "\n -Context:\n " + link.getContext() + "\n -Side Effects:\n " +
                    link.getSideEffects(), link.first.x + 140, link.first.y - 40);
            //gc.setStroke(Color.BLACK);


            gc.strokeRect(link.first.x + 140,
                    link.first.y - 60, 120, 120);

            //gc.strokeOval(link.first.x+40 , link.first.y-30, 100, 100);


            gc.strokeArc(link.first.x + 40 , link.first.y - 30 ,
                    link.x - link.first.x - 80 , 100, 360, 360, ArcType.CHORD);


        }
        else {

            gc.setStroke(Color.BLACK);
            //If it's selected
            if (isSelected) {
                gc.setStroke(Color.RED);
            }
            //Node:
            gc.setFill(Color.WHITESMOKE);
            gc.fillRect(link.first.x - iModel.scrollX + 140,
                    link.first.y - 60 - iModel.scrollY, 120, 120);


            gc.setFill(Color.BLACK);
            gc.fillText(" -Event:\n " + link.getEvent() + "\n -Context:\n " + link.getContext() + "\n -Side Effects:\n " +
                    link.getSideEffects(), link.first.x - iModel.scrollX + 140, link.first.y - iModel.scrollY - 40);
            //gc.setStroke(Color.BLACK);


            gc.strokeRect(link.first.x - iModel.scrollX + 140,
                    link.first.y - 60 - iModel.scrollY, 120, 120);

            //gc.strokeOval(link.first.x+40 , link.first.y-30, 100, 100);


            gc.strokeArc(link.first.x + 40 - iModel.scrollX, link.first.y - 30 - iModel.scrollY,
                    link.x - link.first.x - 80 - iModel.scrollX, 100, 360, 360, ArcType.CHORD);

        }
    }

    private void drawArrow(GraphicsContext gc, double x1, double y1, double x2, double y2){



        gc.setFill(Color.BLACK);

        double dx = x2 - x1, dy = y2 - y1;
        //Get the angle of the arrow (where it's pointing)
        double arrowAngle = Math.atan2(dy, dx);

        int length = (int) Math.sqrt(dx * dx + dy * dy);

        //Save the transform for the scaling
        gc.save();
       Transform arrowTransform = Transform.translate(x1, y1);

        arrowTransform = arrowTransform.createConcatenation(Transform.rotate(Math.toDegrees(arrowAngle), 0, 0));

        gc.transform(new Affine(arrowTransform));



        //Draw the triangle
        gc.fillPolygon(new double[]{length, length - 8, length - 8, length}, new double[]{0, -8, 8, 0},
                4);

        //Draw the link
        gc.strokeLine(0, 0, length, 0);

        //Now we reset the graphics context's transform so it doesn't mess up future drawings
        gc.setTransform(new Affine(Transform.translate(0, 0)));

        //Restore previous transforms
        gc.restore();


    }

    /**
     * This draws the link with the transition node and two arrows. The general idea is
     * to have each arrow roughly one third of the distance, and the node the rest (node will probably be a fixed distance
     * @param gc

     */
    private void drawTransition(GraphicsContext gc, SMTransitionLink link,
                                double[] coords, boolean isSelected, boolean isScaled){

        if(isScaled){
            //First arrow
            drawArrow(gc, coords[0], coords[1], link.x, link.y);

            //Second arrow
            drawArrow(gc, link.x + 120, link.y, coords[2], coords[3]);


            gc.setStroke(Color.BLACK);
            //If it's selected
            if (isSelected) {
                gc.setStroke(Color.RED);
            }

            //Node:
            gc.setFill(Color.WHITESMOKE);
            gc.fillRect(link.x, link.y - 60, 120, 120);


            gc.setFill(Color.BLACK);
            gc.fillText(" -Event:\n " + link.getEvent() + "\n -Context:\n " + link.getContext() + "\n -Side Effects:\n " +
                    link.getSideEffects(), link.x, link.y - 40);
            //gc.setStroke(Color.BLACK);


            gc.strokeRect(link.x, link.y - 60, 120, 120);

        }

        else {


            //First arrow
            drawArrow(gc, coords[0], coords[1], link.x - iModel.scrollX, link.y - iModel.scrollY);

            //Second arrow
            drawArrow(gc, link.x + 120 - iModel.scrollX, link.y - iModel.scrollY, coords[2], coords[3]);


            gc.setStroke(Color.BLACK);
            //If it's selected
            if (isSelected) {
                gc.setStroke(Color.RED);
            }

            //Node:
            gc.setFill(Color.WHITESMOKE);
            gc.fillRect(link.x - iModel.scrollX, link.y - 60 - iModel.scrollY, 120, 120);


            gc.setFill(Color.BLACK);
            gc.fillText(" -Event:\n " + link.getEvent() + "\n -Context:\n " + link.getContext() + "\n -Side Effects:\n " +
                    link.getSideEffects(), link.x - iModel.scrollX, link.y - 40 - iModel.scrollY);
            //gc.setStroke(Color.BLACK);


            gc.strokeRect(link.x - iModel.scrollX, link.y - 60 - iModel.scrollY, 120, 120);
        }
    }

    private void drawLink(boolean isScaled){
        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //gc.clearRect(0,0,viewWidth,viewHeight);
        this.model.getLinks().forEach(l->{
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.BLACK);

            if(l.isFinal){
                //If it's a final one and we need to handle moving nodes it's connected to
                double[] coords = this.model.bestLink(l.first, l.second);

                //Modify them for the scrolling:
                coords[0] -= iModel.scrollX;
                coords[1] -= iModel.scrollY;
                coords[2] -= iModel.scrollX;
                coords[3] -= iModel.scrollY;

                //Check if the transition node is selected:
                if(l.equals(this.iModel.getSelectedNode())) {
                    //If it's a self transition:
                    if(l.first.equals(l.second)){
                        drawSelfTransition(gc, l, true, isScaled);
                    }
                    else {
                        drawTransition(gc, l, coords, true, isScaled);
                    }
                }
                else{
                    //If it's a self transition:
                    if(l.first.equals(l.second)){
                        drawSelfTransition(gc, l, false, isScaled);
                    }
                    else {
                        drawTransition(gc, l, coords, false, isScaled);
                    }

                }


            }
            //If it's a temporary link - if we're drawing links, we aren't panning,
            //So no need to adjust for scroll values
            else {
                gc.strokeLine(l.startX, l.startY, l.endX, l.endY);
            }
        });
    }

    private void drawNode(){
        //I made the rectangle get drawn with the centre of the rectangle
        //where the mouse is clicked - for user's ease of use
        gc.clearRect(0,0,viewWidth,viewHeight);
        this.model.getNodes().forEach(n->{

            if(n.equals(iModel.getSelectedNode())) {
                //Make border red for selected
                gc.setFill(Color.YELLOW);
                gc.fillRect(n.x - 40 - iModel.scrollX, n.y - 30 - iModel.scrollY, 80, 60);


                gc.setFill(Color.RED);
                gc.fillText(n.getName(), n.x-40 - iModel.scrollX, n.y - iModel.scrollY);


                gc.setStroke(Color.RED);
                gc.strokeRect(n.x - 40 - iModel.scrollX, n.y - 30 - iModel.scrollY, 80, 60);
            }
            else{

                gc.setFill(Color.YELLOW);
                gc.fillRect(n.x - 40 - iModel.scrollX, n.y - 30 - iModel.scrollY, 80, 60);
                gc.setStroke(Color.BLACK);

                gc.setFill(Color.BLACK);
                gc.fillText(n.getName(), n.x-40 - iModel.scrollX, n.y - iModel.scrollY);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(n.x - 40 - iModel.scrollX, n.y - 30 - iModel.scrollY, 80, 60);


            }


        });



    }

    public void setIModel(InteractionModel iModel){
        this.iModel = iModel;
        this.iModel.setViewWidth(viewWidth);
        this.iModel.setViewHeight(viewHeight);
    }

    public void setModel(SMModel model){

        this.model = model;

    }

    public void iModelChanged(){
        //drawView();

        drawNode();
        drawLink(false);
        this.canvas.setCursor(iModel.getCursor());
        drawOverlay();



    }

    public void modelChanged(){

        drawNode();
        drawLink(false);
        drawOverlay();


    }

    public void setController(NodeController controller){

        this.canvas.setOnMousePressed(controller::handlePressed);
        this.canvas.setOnMouseDragged(controller::handleDragged);
        this.canvas.setOnMouseReleased(controller::handleReleased);

        this.canvas.setOnKeyPressed(event -> {
            if( event.getCode() == KeyCode.BACK_SPACE) {
                controller.handleDelete();
            }
        } );


    }




}
