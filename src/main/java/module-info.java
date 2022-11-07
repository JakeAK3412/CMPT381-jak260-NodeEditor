module com.example.cmpt381jak260nodeeditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cmpt381jak260nodeeditor to javafx.fxml;
    exports com.example.cmpt381jak260nodeeditor;
}