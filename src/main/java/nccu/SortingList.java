package nccu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class SortingList {
    private static ObservableList<Course> list = FXCollections.observableArrayList();

    public static ObservableList<Course> getSortList() {
        return list;
    }

    public static void add(Course course) {
        list.add(course);
    }

    public static TableView<Course> setDnD(TableView<Course> sortingTable) {
        try {
            sortingTable.setRowFactory(table -> {
                TableRow<Course> row = new TableRow<>();

                row.setOnDragDetected(event -> {
                    if (!row.isEmpty()) {
                        Integer index = row.getIndex();
                        Dragboard dragboard = row.startDragAndDrop(TransferMode.MOVE);
                        dragboard.setDragView(row.snapshot(null, null));
                        ClipboardContent clipboard = new ClipboardContent();
                        clipboard.put(SetDataFormat.getDataFormat(), index);
                        dragboard.setContent(clipboard);
                        event.consume();
                    }
                });

                row.setOnDragOver(event -> {
                    Dragboard dragboard = event.getDragboard();
                    if (dragboard.hasContent(SetDataFormat.getDataFormat())) {
                        if (row.getIndex() != ((Integer) dragboard.getContent(SetDataFormat.getDataFormat()))
                                .intValue()) {
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                            event.consume();
                        }
                    }
                });

                row.setOnDragDropped(event -> {
                    Dragboard dragboard = event.getDragboard();
                    if (dragboard.hasContent(SetDataFormat.getDataFormat())) {
                        int draggedIndex = (Integer) dragboard.getContent(SetDataFormat.getDataFormat());
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
        } catch (IllegalArgumentException e) {
            System.out.println("catch");
        }

        return sortingTable;
    }
}
