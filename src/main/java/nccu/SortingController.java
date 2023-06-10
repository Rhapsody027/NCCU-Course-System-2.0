package nccu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class SortingController implements Initializable {
    @FXML
    private Pane paintable_grid;

    @FXML
    private StackPane root;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Paintable paintable = new Paintable();
        int columns = paintable.getColumns();
        int rows = paintable.getRows();
        double width = paintable.getWidth();
        double height = paintable.getWidth();
        ImageView imageView = paintable.getImageView();
    }

    @FXML
    public void switchScene(ActionEvent event) {
        try {
            App.setRoot("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
