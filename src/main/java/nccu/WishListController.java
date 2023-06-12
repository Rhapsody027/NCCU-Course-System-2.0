package nccu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class WishListController implements Initializable {

    @FXML
    private TableView<Course> wishTable;

    @FXML
    private TableColumn<Course, String> att;

    @FXML
    private TableColumn<Course, String> id;

    @FXML
    private TableColumn<Course, Integer> cool;

    @FXML
    private TableColumn<Course, String> name;

    @FXML
    private TableColumn<Course, String> pro;

    @FXML
    private TableColumn<Course, Integer> sweet;

    @FXML
    private TableColumn<Course, String> time;

    @FXML
    private GridPane paintable_grid;

    ObservableList<Course> wishList = WishList.getWishList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // set tableView cell value
        id.setCellValueFactory(new PropertyValueFactory<Course, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        time.setCellValueFactory(new PropertyValueFactory<Course, String>("time"));
        pro.setCellValueFactory(new PropertyValueFactory<Course, String>("pro"));
        sweet.setCellValueFactory(new PropertyValueFactory<Course, Integer>("sweet"));
        cool.setCellValueFactory(new PropertyValueFactory<Course, Integer>("cool"));
        att.setCellValueFactory(new PropertyValueFactory<Course, String>("att"));

        // setup DnD table
        wishTable = WishList.setDnD(wishTable);
        wishTable.setItems(WishList.getWishList());

        // setup color table
        setupColorGrid();
    }

    public void setupColorGrid() {
        int NUM_ROWS = 7;
        int NUM_COLS = 5;
        double recWidth = 75;
        double recHeight = 90.5;

        for (int time = 1; time <= NUM_ROWS; time++) {
            for (int day = 1; day <= NUM_COLS; day++) {

                Rectangle rect = new Rectangle(recWidth, recHeight);
                rect.setFill(getColorForCell(day, time));
                rect.setStroke(Color.SKYBLUE);

                // // add text to rect
                // StackPane stack = new StackPane();
                // Text txt = new Text("test");
                // stack.getChildren().addAll(rect, txt);

                // turn time into grid index
                paintable_grid.add(rect, day - 1, time - 1);
            }
        }
    }

    private Color getColorForCell(int day, int time) {

        for (Course course : wishList) {
            String courseTime = course.getTime();

            // * time + 8 is becuz the diff between index and actual time
            // * hence, need to find a way to store time
            if (day == TimeFormater.getDay(courseTime) &&
                    TimeFormater.getSession(courseTime).contains(time + 8)) {
                return Color.SKYBLUE;
            }
        }

        return Color.WHITE;

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
