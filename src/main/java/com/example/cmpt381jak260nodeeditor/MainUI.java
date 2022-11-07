package com.example.cmpt381jak260nodeeditor;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MainUI extends StackPane {



    public MainUI(){


        //Label label = new Label("Node Editor");
        NodeController controller = new NodeController();
        ToolPalette toolPalette = new ToolPalette();

        InteractionModel iModel = new InteractionModel();

        DiagramView diagramView = new DiagramView();
        SMModel model = new SMModel();


        model.addSubscriber(diagramView);

        iModel.addSubscriber(toolPalette);
        iModel.addSubscriber(diagramView);

        toolPalette.setController(controller);
        toolPalette.setInteractionModel(iModel);

        diagramView.setIModel(iModel);
        diagramView.setController(controller);
        diagramView.setModel(model);

        controller.setIModel(iModel);
        controller.setModel(model);




        HBox hbox = new HBox(toolPalette, diagramView);

        this.getChildren().addAll(hbox);

        toolPalette.init();



    }
}
