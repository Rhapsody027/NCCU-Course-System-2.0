package nccu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class SortingController implements Initializable {

    @FXML
    private TableView<Course> sortingTable;

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

        // set tableView cell value
        id.setCellValueFactory(new PropertyValueFactory<Course, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        time.setCellValueFactory(new PropertyValueFactory<Course, String>("time"));
        pro.setCellValueFactory(new PropertyValueFactory<Course, String>("pro"));
        sweet.setCellValueFactory(new PropertyValueFactory<Course, Integer>("sweet"));
        cool.setCellValueFactory(new PropertyValueFactory<Course, Integer>("cool"));
        att.setCellValueFactory(new PropertyValueFactory<Course, String>("att"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HomeController homeController = loader.getController();

        // setup DnD table
        sortingTable.setItems(homeController.getCourseSelect());

        sortingTable.setRowFactory(table -> {
            TableRow<Course> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard dragboard = row.startDragAndDrop(TransferMode.MOVE);
                    dragboard.setDragView(row.snapshot(null, null));
                    ClipboardContent clipboard = new ClipboardContent();
                    clipboard.put(SERIALIZED_MIME_TYPE, index);
                    dragboard.setContent(clipboard);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard dragboard = event.getDragboard();
                if (dragboard.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer) dragboard.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard dragboard = event.getDragboard();
                if (dragboard.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) dragboard.getContent(SERIALIZED_MIME_TYPE);
                    Course draggedCourse = sortingTable.getItems().remove(draggedIndex);

                    int dropIndex;

                    if (row.isEmpty()) {
                        dropIndex = sortingTable.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    sortingTable.getItems().add(dropIndex, draggedCourse);

                    event.setDropCompleted(true);
                    sortingTable.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });

            return row;
        });
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
