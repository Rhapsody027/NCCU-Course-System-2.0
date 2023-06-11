// package nccu;

// import java.util.function.Function;

// import javafx.application.Application;
// import javafx.beans.value.ObservableValue;
// import javafx.scene.Scene;
// import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableRow;
// import javafx.scene.control.TableView;
// import javafx.scene.input.ClipboardContent;
// import javafx.scene.input.DataFormat;
// import javafx.scene.input.Dragboard;
// import javafx.scene.input.TransferMode;
// import javafx.scene.layout.BorderPane;
// import javafx.stage.Stage;

// public class OverflowDnD extends Application {

// private static final DataFormat SERIALIZED_MIME_TYPE = new
// DataFormat("application/x-java-serialized-object");

// @Override
// public void start(Stage primaryStage) {
// TableView<Course> tableView = new TableView<>();
// tableView.getColumns().add(createCol("course Name",
// Course::courseNameProperty, 150));
// tableView.getItems().addAll(new Course("test"), new Course("test2"));

// tableView.setRowFactory(table -> {
// TableRow<Course> row = new TableRow<>();

// row.setOnDragDetected(event -> {
// if (!row.isEmpty()) {
// Integer index = row.getIndex();
// Dragboard dragboard = row.startDragAndDrop(TransferMode.MOVE);
// dragboard.setDragView(row.snapshot(null, null));
// ClipboardContent clipboard = new ClipboardContent();
// clipboard.put(SERIALIZED_MIME_TYPE, index);
// dragboard.setContent(clipboard);
// event.consume();
// }
// });

// row.setOnDragOver(event -> {
// Dragboard dragboard = event.getDragboard();
// if (dragboard.hasContent(SERIALIZED_MIME_TYPE)) {
// if (row.getIndex() != ((Integer)
// dragboard.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
// event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
// event.consume();
// }
// }
// });

// row.setOnDragDropped(event -> {
// Dragboard dragboard = event.getDragboard();
// if (dragboard.hasContent(SERIALIZED_MIME_TYPE)) {
// int draggedIndex = (Integer) dragboard.getContent(SERIALIZED_MIME_TYPE);
// Course draggedCourse = tableView.getItems().remove(draggedIndex);

// int dropIndex;

// if (row.isEmpty()) {
// dropIndex = tableView.getItems().size();
// } else {
// dropIndex = row.getIndex();
// }

// tableView.getItems().add(dropIndex, draggedCourse);

// event.setDropCompleted(true);
// tableView.getSelectionModel().select(dropIndex);
// event.consume();
// }
// });

// return row;
// });

// Scene scene = new Scene(new BorderPane(tableView), 600, 400);
// primaryStage.setScene(scene);
// primaryStage.show();
// }

// private TableColumn<Course, String> createCol(String title,
// Function<Course, ObservableValue<String>> mapper, double size) {

// TableColumn<Course, String> col = new TableColumn<>(title);
// col.setCellValueFactory(cellData -> mapper.apply(cellData.getValue()));
// col.setPrefWidth(size);

// return col;
// }

// }