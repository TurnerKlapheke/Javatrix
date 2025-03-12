package com.tetris;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;

public class MiniBlock extends Rectangle {
    // fields
    private int row;
    private int col;
    private Color color;
    private int id;

    // constructor
    public MiniBlock(Color color, int row, int col, int id) {
        super(30, 30, color);
        this.row = row;
        this.col = col;
        this.color = color;
        this.setFill(color);
        this.id = id;
    }

    // variant constructor for testing miniblocks on their own (not associated w/ a
    // block with an id)
    public MiniBlock(Color color, int row, int col) {
        super(30, 30, color);
        this.row = row;
        this.col = col;
        this.color = color;
        this.setFill(color);
        this.id = -1;
    }

    // methods

    // Helper method to update the GridPane position
    public void updatePosition(GridPane pane) {
        // If not already added, add this node.
        if (!pane.getChildren().contains(this)) {
            pane.getChildren().add(this);
        }
        GridPane.setRowIndex(this, row);
        GridPane.setColumnIndex(this, col);
    }

    public Boolean moveDown(GridPane pane) {
        if (GameManager.touching(Direction.DOWN, this)) {
            return false;
        }
        row++;
        updatePosition(pane);
        return true;
    }

    // implementation is MORE SIMILAR TO ROTATE in that it only changes the object's
    // properties rather than where it is on the pane
    public Boolean moveLeft() {
        col--;
        return true;
    }

    public Boolean moveRight() {
        col++;
        return true;
    }

    /**
     * public Boolean touching(String direction, GridPane pane) {
     * if (direction.equals("down")) {
     * if (row == 19) {
     * return true;
     * } else {
     * int row = this.getRow() + 1;
     * int column = this.getCol();
     * for (Node node : pane.getChildren()) {
     * if (GridPane.getColumnIndex(node) == row && GridPane.getColumnIndex(node) ==
     * column) {
     * return true;
     * }
     * }
     * return false;
     * }
     * } else if (direction.equals("left")) {
     * return false;
     * } else if (direction.equals("right")) {
     * return false;
     * } else if (direction.equals("up")) {
     * return false;
     * } else {
     * return false;
     * }
     * 
     * }
     **/

    public int getRow() {
        return this.row;
    }

    public void setRow(int r) {
        this.row = r;
    }

    public int getCol() {
        return this.col;
    }

    public Color getColor() {
        return this.color;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return "Row: " + this.row + ", Col: " + this.col + ", Color: " + this.color;
    }

    // Override equals to include the unique id along with row and col.
    // @Override
    // public boolean equals(Object other) {
    //     if (this == other) return true;
    //     if (!(other instanceof MiniBlock)) return false;
    //     MiniBlock that = (MiniBlock) other;
    //     return this.row == that.row && this.col == that.col && this.id == that.id;
    // }

    // @Override
    // public int hashCode() {
    //     int result = 17;
    //     result = 31 * result + row;
    //     result = 31 * result + col;
    //     result = 31 * result + id;
    //     return result;
    // }
}
