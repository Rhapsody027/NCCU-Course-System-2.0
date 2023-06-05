package nccu;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField txt_search;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        name.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        time.setCellValueFactory(new PropertyValueFactory<Course, String>("time"));
        pro.setCellValueFactory(new PropertyValueFactory<Course, String>("pro"));
        sweet.setCellValueFactory(new PropertyValueFactory<Course, Integer>("sweet"));
        cool.setCellValueFactory(new PropertyValueFactory<Course, Integer>("cool"));
        att.setCellValueFactory(new PropertyValueFactory<Course, String>("att"));

        try {

            // import database to tableview
            ObservableList<Course> list = DBConnect.getData();
            search_table.setItems(list);
            System.out.println("Import!");

            // init search list
            FilteredList<Course> filteredList = new FilteredList<>(list, b -> true);
            txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(Course -> {

                    // txt_search is empty
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }

                    // if txt_search has a match name
                    String keyWord = newValue;
                    if (Course.getName().indexOf(keyWord) > -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            SortedList<Course> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(search_table.comparatorProperty());
            search_table.setItems(sortedList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void search(String search_text) {
        search_table.getItems().stream()
                .filter(item -> item.getName() == search_text)
                .findAny()
                .ifPresent(item -> {
                    search_table.getSelectionModel().select(item);
                    search_table.scrollTo(item);
                });
    }
}
