package com.example.cmpt381jak260nodeeditor;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

//import static jdk.xml.internal.SecuritySupport.getClassLoader;

public class ToolPalette extends StackPane implements IModelListener {

    public ArrayList<ModeButton> tools;

    InteractionModel iModel;


    public ToolPalette(){

        VBox vbox = new VBox();






        ModeButton cursorButton = new ModeButton(Tools.CURSOR, "src/main/java/com/example/cmpt381jak260nodeeditor/cursor.png");

        ModeButton moveButton = new ModeButton(Tools.MOVE, "src/main/java/com/example/cmpt381jak260nodeeditor/crosshair.png");

        ModeButton linkButton = new ModeButton(Tools.LINK, "src/main/java/com/example/cmpt381jak260nodeeditor/linktool.png");

        this.tools = new ArrayList<>();

        this.tools.add(cursorButton);
        this.tools.add(moveButton);
        this.tools.add(linkButton);

        vbox.getChildren().addAll(cursorButton, moveButton, linkButton);

        vbox.setSpacing(10);

        vbox.setPadding(new Insets(0, 5, 0, 5));

        this.setPrefWidth(60);

        this.getChildren().addAll(vbox);

    }

    public void setInteractionModel(InteractionModel im) {
        this.iModel = im;
    }

    public void setController(NodeController controller) {
        this.tools.forEach(t -> {
            t.setOnAction(e -> controller.handleToolChanged(t.tool));
        });
    }

    public void init(){
        this.tools.get(0).fire();
    }

    @Override
    public void iModelChanged() {


            this.tools.forEach(t -> {
                t.unselect();
                if (t.tool == iModel.getSelectedTool()) t.select();
            });


    }
}
