module nccu {
    requires javafx.controls;
    requires javafx.fxml;

    opens nccu to javafx.fxml;
    exports nccu;
}
