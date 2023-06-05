module nccu {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive java.sql;
    requires javafx.web;
    // requires jxbrowser;

    opens nccu to javafx.fxml;

    exports nccu;
}
