package nccu;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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

    @FXML
    private ChoiceBox<String> box_day;

    @FXML
    private ChoiceBox<String> box_time;

    @FXML
    private ChoiceBox<String> box_type;

    @FXML
    private TextArea comment_txt;

    @FXML
    private ListView<String> comment_list;

    @FXML
    private Button btn_post;

    private ObservableList<Course> search_list;
    private FilteredList<Course> filteredList;
    private SortedList<Course> sortedList;

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

        // import database to tableview
        search_list = DBConnect.getCourseData();
        search_table.setItems(search_list);
        System.out.println("Import!");

        // setup search_bar
        filteredList = new FilteredList<>(search_list, b -> true);
        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Course -> {

                if (Course.getName().indexOf(newValue) > -1) {
                    return true;
                } else if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        refreshTableFilter();

        // init webEngine
        engine = webView.getEngine();

        // init choiceBoxes
        String[] days = { null, "一", "二", "三", "四", "五" };
        box_day.getItems().addAll(days);
        box_day.setOnAction(this::getChoiceFilter);

        String[] time = { null, "早上09-12", "下午13-16", "下午16-18", "晚上18-21" };
        box_time.getItems().addAll(time);
        box_time.setOnAction(this::getChoiceFilter);

        String[] types = { null, "國文", "英文", "體育", "必修", "通識" };
        box_type.getItems().addAll(types);
        box_type.setOnAction(this::getChoiceFilter);

        // setup comment function
        btn_post.setOnAction(event -> postMessage());
        Comment.updateMessageList(comment_list);

    }

    public void postMessage() {
        String content = comment_txt.getText();
        comment_txt.clear();

        if (content != "") {
            Comment.insertMessage(content);
            comment_list = Comment.updateMessageList(comment_list);
        }
    }

    public void refreshTableFilter() {
        sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(search_table.comparatorProperty());
        search_table.setItems(sortedList);
    }

    @FXML
    public void getChoiceFilter(ActionEvent event) {

        Predicate<Course> combinedPredicate = null;
        Predicate<Course> dayPredicate;
        Predicate<Course> timePredicate;
        Predicate<Course> typePredicate;

        if (box_day.getValue() != null) {
            dayPredicate = Course -> Course.getTime().contains(box_day.getValue());
        } else {
            dayPredicate = Course -> true;
        }

        if (box_time.getValue() != null) {
            timePredicate = Course -> {
                return inTimeSeclect(Course.getTime(), box_time.getValue());
            };
        } else {
            timePredicate = Course -> true;
        }

        // * need to add a tag for course type, such as "必修", "人文通識" etc.
        if (box_type.getValue() != null) {
            String[] alt_way = { "國文", "英文", "體育", "學", "管理", "入門" };
            typePredicate = Course -> {
                if (box_type.getValue() == "通識") {
                    boolean flag = true;
                    for (String keyword : alt_way) {
                        if (Course.getName().contains(keyword)) {
                            flag = false;
                        }
                    }
                    return flag;

                } else {
                    return Course.getName().contains(box_type.getValue());
                }
            };
        } else {
            typePredicate = Course -> true;
        }

        combinedPredicate = dayPredicate.and(timePredicate).and(typePredicate);
        filteredList.setPredicate(combinedPredicate);
        refreshTableFilter();
    }

    // * use String? int? another property?
    public boolean inTimeSeclect(String courseTime, String timeSelect) {
        String[] time_session = {};

        for (String time : time_session) {
            if (courseTime.contains(time)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public void getTagFilter(ActionEvent event) {
        int sweetStandard = 5;
        int coolStandard = 5;

        Predicate<Course> combinedPredicate = null;
        Predicate<Course> sweetPredicate;
        Predicate<Course> coolPredicate;
        Predicate<Course> attPredicate;

        if (rad_sweet.isSelected()) {
            sweetPredicate = Course -> Course.getSweet() >= sweetStandard;
        } else {
            sweetPredicate = Course -> true;
        }

        if (rad_cool.isSelected()) {
            coolPredicate = Course -> Course.getCool() >= coolStandard;
        } else {
            coolPredicate = Course -> true;
        }

        if (rad_att.isSelected()) {
            attPredicate = Course -> Course.getAtt().equals("否");
        } else {
            attPredicate = Course -> true;
        }

        combinedPredicate = sweetPredicate.and(coolPredicate).and(attPredicate);
        filteredList.setPredicate(combinedPredicate);
        refreshTableFilter();
    }

    // load webViewl when table is double-licked (#0db4be)
    // * need to find a way to load file automatically
    @FXML
    public void loadWebView(MouseEvent event) {
        int index = search_table.getSelectionModel().getSelectedIndex();

        if (index > -1 && event.getClickCount() == 2) {
            String fileName = id.getCellData(index).toString().strip();
            File f = new File("pages/" + fileName + ".html");
            System.out.println(fileName); // * first col has a strange '?'

            engine.load(f.toURI().toString());
        }
    }
}
