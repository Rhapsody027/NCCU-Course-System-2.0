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
    private TableView<test> search_table;

    @FXML
    private TableColumn<test, String> name;

    @FXML
    private TableColumn<test, String> pro;

    @FXML
    private TableColumn<test, String> time;

    @FXML
    private TableColumn<test, String> tag;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        name.setCellValueFactory(new PropertyValueFactory<test, String>("name"));
        pro.setCellValueFactory(new PropertyValueFactory<test, String>("pro"));
        time.setCellValueFactory(new PropertyValueFactory<test, String>("time"));
        tag.setCellValueFactory(new PropertyValueFactory<test, String>("tag"));

        try {
            ObservableList<test> list = DBConnect.getData();
            search_table.setItems(list);
            System.out.println("Hiiii");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
