package nccu;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController implements Initializable {

    @FXML
    private TableColumn<Course, String> att;

    @FXML
    private TableColumn<Course, Integer> cool;

    @FXML
    private TableColumn<Course, String> name;

    @FXML
    private TableColumn<Course, String> pro;

    @FXML
    private TableView<Course> search_table;

    @FXML
    private TableColumn<Course, Integer> sweet;

    @FXML
    private TableColumn<Course, String> time;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        name.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        time.setCellValueFactory(new PropertyValueFactory<Course, String>("time"));
        pro.setCellValueFactory(new PropertyValueFactory<Course, String>("pro"));
        sweet.setCellValueFactory(new PropertyValueFactory<Course, Integer>("sweet"));
        cool.setCellValueFactory(new PropertyValueFactory<Course, Integer>("cool"));
        att.setCellValueFactory(new PropertyValueFactory<Course, String>("att"));

        try {
            ObservableList<Course> list = DBConnect.getData();
            search_table.setItems(list);
            System.out.println("Import!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
