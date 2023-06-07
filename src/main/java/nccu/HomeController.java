package nccu;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
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

    @FXML
    private RadioButton rad_att;

    @FXML
    private RadioButton rad_cool;

    @FXML
    private RadioButton rad_sweet;

    private ObservableList<Course> search_list;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        id.setCellValueFactory(new PropertyValueFactory<Course, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        time.setCellValueFactory(new PropertyValueFactory<Course, String>("time"));
        pro.setCellValueFactory(new PropertyValueFactory<Course, String>("pro"));
        sweet.setCellValueFactory(new PropertyValueFactory<Course, Integer>("sweet"));
        cool.setCellValueFactory(new PropertyValueFactory<Course, Integer>("cool"));
        att.setCellValueFactory(new PropertyValueFactory<Course, String>("att"));

        // import database to tableview
        search_list = DBConnect.getData();
        search_table.setItems(search_list);
        System.out.println("Import!");

        // init search list
        FilteredList<Course> filteredList = new FilteredList<>(search_list, b -> true);
        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Course -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // if it has match results
                String keyWord = newValue;
                // if (search(Course, keyWord) > -1) {
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

    }

    // filter table when type in search bar
    public int search(Course course, String keyWord) {
        return course.getName().indexOf(keyWord);
    }

    public void getTagFilter(ActionEvent event) throws SQLException {
        if (rad_sweet.isSelected()) {
            FilteredList<Course> filteredList = new FilteredList<>(search_list, b -> true);
            filteredList.setPredicate(Course -> Course.getSweet() == 5);
            search_table.setItems(new SortedList<>(filteredList));

        } else if (!rad_sweet.isSelected()) {
            search_table.setItems(search_list);
        }
        if (rad_cool.isSelected())

        {
            System.out.println(rad_cool.getId());
        }
        if (rad_att.isSelected()) {
            System.out.println(rad_att.getId());
        }
    }

    // load webViewl when table is double-licked (#0db4be)
    // * need to find a way to load file automatically
    public void tableClick(MouseEvent event) {
        int index = search_table.getSelectionModel().getSelectedIndex();

        if (index > -1 && event.getClickCount() == 2) {
            String fileName = id.getCellData(index).toString().strip();
            File f = new File("pages/" + fileName + ".html");
            System.out.println(fileName); // * first col has a strange '?'

            engine.load(f.toURI().toString());
        }
    }
}
