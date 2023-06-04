module nccu {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive java.sql;

    opens nccu to javafx.fxml;

    exports nccu;
}
