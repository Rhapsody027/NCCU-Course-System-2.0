package nccu;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Paintable {
    boolean showHoverCursor;
    int rows, columns;
    double width, height;
    ImageView imageView;

    public Paintable() {
        showHoverCursor = true;

        rows = 7;
        columns = 5;
        width = 800;
        height = 500;

        imageView = new ImageView(
                new Image("https://upload.wikimedia.org/wikipedia/commons/c/c7/Pink_Cat_2.jpg"));

    }

    public boolean isShowHoverCursor() {
        return showHoverCursor;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public ImageView getImageView() {
        return imageView;
    }
}

class Grid extends Pane {

    int rows;
    int columns;

    double width;
    double height;

    Cell[][] cells;

    public Grid(int columns, int rows, double width, double height) {

        this.columns = columns;
        this.rows = rows;
        this.width = width;
        this.height = height;

        cells = new Cell[rows][columns];

    }

    /**
     * Add cell to array and to the UI.
     */
    public void add(Cell cell, int column, int row) {

        cells[row][column] = cell;

        double w = width / columns;
        double h = height / rows;
        double x = w * column;
        double y = h * row;

        cell.setLayoutX(x);
        cell.setLayoutY(y);
        cell.setPrefWidth(w);
        cell.setPrefHeight(h);

        getChildren().add(cell);

    }

    public Cell getCell(int column, int row) {
        return cells[row][column];
    }

    /**
     * Unhighlight all cells
     */
    public void unhighlight() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells[row][col].unhighlight();
            }
        }
    }
}

class Cell extends StackPane {

    int column;
    int row;

    public Cell(int column, int row) {

        this.column = column;
        this.row = row;

        getStyleClass().add("cell");

        // Label label = new Label(this.toString());
        //
        // getChildren().add(label);

        setOpacity(0.9);
    }

    public void highlight() {
        // ensure the style is only once in the style list
        getStyleClass().remove("cell-highlight");

        // add style
        getStyleClass().add("cell-highlight");
    }

    public void unhighlight() {
        getStyleClass().remove("cell-highlight");
    }

    public void hoverHighlight() {
        // ensure the style is only once in the style list
        getStyleClass().remove("cell-hover-highlight");

        // add style
        getStyleClass().add("cell-hover-highlight");
    }

    public void hoverUnhighlight() {
        getStyleClass().remove("cell-hover-highlight");
    }

    public String toString() {
        return this.column + "/" + this.row;
    }
}

class MouseGestures {
    boolean showHoverCursor = true;

    public void makePaintable(Node node) {

        // that's all there is needed for hovering, the other code is just for painting
        if (showHoverCursor) {
            node.hoverProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                        Boolean newValue) {

                    if (newValue) {
                        ((Cell) node).hoverHighlight();
                    } else {
                        ((Cell) node).hoverUnhighlight();
                    }

                }

            });
        }

        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnDragDetected(onDragDetectedEventHandler);
        node.setOnMouseDragEntered(onMouseDragEnteredEventHandler);

    }

    EventHandler<MouseEvent> onMousePressedEventHandler = event -> {

        Cell cell = (Cell) event.getSource();

        if (event.isPrimaryButtonDown()) {
            cell.highlight();
        } else if (event.isSecondaryButtonDown()) {
            cell.unhighlight();
        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {

        PickResult pickResult = event.getPickResult();
        Node node = pickResult.getIntersectedNode();

        if (node instanceof Cell) {

            Cell cell = (Cell) node;

            if (event.isPrimaryButtonDown()) {
                cell.highlight();
            } else if (event.isSecondaryButtonDown()) {
                cell.unhighlight();
            }

        }

    };

    EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
    };

    EventHandler<MouseEvent> onDragDetectedEventHandler = event -> {

        Cell cell = (Cell) event.getSource();
        cell.startFullDrag();

    };

    EventHandler<MouseEvent> onMouseDragEnteredEventHandler = event -> {

        Cell cell = (Cell) event.getSource();

        if (event.isPrimaryButtonDown()) {
            cell.highlight();
        } else if (event.isSecondaryButtonDown()) {
            cell.unhighlight();
        }

    };

}