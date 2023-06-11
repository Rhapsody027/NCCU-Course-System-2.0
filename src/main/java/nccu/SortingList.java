package nccu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SortingList {
    private static ObservableList<Course> list = FXCollections.observableArrayList();

    public static ObservableList<Course> getList() {
        return list;
    }

    public static void add(Course course) {
        list.add(course);
    }
}
