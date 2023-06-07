package nccu;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HomeController implements Initializable {

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
    private TableView<Course> search_table;

    @FXML
    private TableColumn<Course, Integer> sweet;

    @FXML
    private TableColumn<Course, String> time;

    @FXML
    private TextField search_text;

    @FXML
    private WebView webView;
    private WebEngine engine;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        id.setCellValueFactory(new PropertyValueFactory<Course, String>("id"));
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
            search_text.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(Course -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }

                    // if it has match results
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

            // init webEngine
            engine = webView.getEngine();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // filter table when type in search bar
    public void search(String search_text) {
        search_table.getItems().stream()
                .filter(item -> item.getName() == search_text)
                .findAny()
                .ifPresent(item -> {
                    search_table.getSelectionModel().select(item);
                    search_table.scrollTo(item);
                });
    }

    // load correct page when table is clicked
    @FXML
    public void tableClick(MouseEvent event) {
        int index = search_table.getSelectionModel().getSelectedIndex();

        if (index > -1 && event.getClickCount() == 2) {
            String fileName = id.getCellData(index).toString().strip();
            loadPage(fileName);
            System.out.println(fileName); // * first col has a strange '?'
        }

    }

    // load local html file to right-top webview
    // * need to find a way to load file automatically
    public void loadPage(String fileName) {
        try {
            File f = new File("pages/" + fileName + ".html");
            engine.load(f.toURI().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
