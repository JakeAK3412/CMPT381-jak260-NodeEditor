package com.example.cmpt381jak260nodeeditor;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainUI extends StackPane {

    LinkPropertiesView linkView;
    NodePropertiesView nodeView;

    public MainUI(Stage stage){


        //Label label = new Label("Node Editor");

        ToolPalette toolPalette = new ToolPalette();

        InteractionModel iModel = new InteractionModel();

        DiagramView diagramView = new DiagramView(800, 800);
        SMModel model = new SMModel();

        this.linkView = new LinkPropertiesView();
        this.linkView.setVisible(false);
        this.linkView.setManaged(false);

        this.nodeView = new NodePropertiesView();

        NodeController controller = new NodeController(this.linkView, this.nodeView);


        this.nodeView.setController(controller);
        this.linkView.setController(controller);

        model.addSubscriber(diagramView);

        iModel.addSubscriber(toolPalette);
        iModel.addSubscriber(diagramView);
        iModel.setWorldHeight(1600);
        iModel.setWorldWidth(1600);

        toolPalette.setController(controller);
        toolPalette.setInteractionModel(iModel);

        diagramView.setIModel(iModel);
        diagramView.setController(controller);
        diagramView.setModel(model);
        //diagramView.setMaxSize(800, 800);

        controller.setIModel(iModel);
        controller.setModel(model);




        HBox hbox = new HBox(toolPalette, diagramView, nodeView, linkView);

        this.getChildren().addAll(hbox);

        this.setMinWidth(500);


        //this.widthProperty().addListener(diagramView::setCanvasWidth);
        //this.heightProperty().addListener(diagramView::setCanvasHeight);


        //Initial calls for certain methods
        toolPalette.init();
        diagramView.drawOverlayRect();



    }

    public void layoutChildren(){

    }
}
