package nccu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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

    @FXML
    private Button btn_dele;

    @FXML
    private Button btn_link;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_home;

    @FXML
    private AnchorPane anchorPane;

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

        // setup btn
        btn_dele.getStyleClass().add("excluded-button");
        btn_link.getStyleClass().add("excluded-button");
    }

    public void setupColorGrid() {
        double recWidth = 75;
        double recHeight = 90.5;

        // day for col, time for row
        for (Course course : wishList) {
            int day = TimeFormater.getDay(course.getTime());
            ArrayList<Integer> time = TimeFormater.getSession(course.getTime());

            for (int t : time) {
                Rectangle rect = new Rectangle(recWidth, recHeight);
                rect.setFill(Color.SKYBLUE);
                rect.setStroke(Color.SKYBLUE);

                // add text to rect
                StackPane stack = new StackPane();
                Text txt = new Text(course.getName());

                if (course.getName().length() > 3) {
                    txt = new Text(course.getName().substring(0, 2) + "...");
                } else {
                    txt = new Text(course.getName());
                }

                stack.getChildren().addAll(rect, txt);

                // * time - 8 is becuz the diff between index and actual time
                // * hence, need to find a way to store time
                paintable_grid.add(stack, day - 1, t - 8 - 1);
            }
        }
    }

    @FXML
    public void switchScene(ActionEvent event) {
        try {
            App.setRoot("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void delete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("確認刪除");
        alert.setHeaderText("確認移除課程?");
        alert.showAndWait();

        Course selectedCourse = wishTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            wishList.remove(selectedCourse);
        }
    }

    @FXML
    public void save(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("確認儲存");
        alert.setHeaderText("已儲存志願清單!");
        alert.showAndWait();

        for (Course course : wishList) {
            DBConnect.saveWishList(course.getId(), course.getName(),
                    course.getTime(), course.getPro());
        }
    }
}

// * refresh table