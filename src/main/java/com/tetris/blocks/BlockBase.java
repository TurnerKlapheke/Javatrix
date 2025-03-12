package com.tetris.blocks;

import java.util.ArrayList;

import com.tetris.Direction;
import com.tetris.GameManager;
import com.tetris.MiniBlock;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class BlockBase {
    // fields
    private ArrayList<MiniBlock> components;
    private Color color;
    private int topLeftR;
    private int topLeftC;
    private int rotState = 0;
    private int[][][] configs;
    private static int highest_id = 0;
    private int id;

    // constructor
    public BlockBase(Color color, int[][][] configs, int topLeftR, int topLeftC) {
        this.components = new ArrayList<>();
        this.topLeftC = topLeftC;
        this.topLeftR = topLeftR;
        this.configs = configs;
        this.color = color;
        this.id = highest_id;
        highest_id++;
        for (int r = 0; r < configs[0].length; r++) {
            for (int c = 0; c < configs[0][r].length; c++) {
                if ((configs[0][r][c] == 1)) {
                    components.add(new MiniBlock(color, topLeftR + r, topLeftC + c, id));
                }
            }

        }
    }

    // methods
    public Boolean moveDown(GridPane pane) {
        // Check all components first
        for (MiniBlock block : components) {
            if (GameManager.touching(Direction.DOWN, block)) {
                return false;
            }
        }
        
        // Move all components
        for (MiniBlock block : components) {
            block.moveDown(pane);
        }
        topLeftR++;
        return true;
    }

    public Boolean rotate() {
        int nextState = (rotState + 1) % 4;
        ArrayList<MiniBlock> newComponents = new ArrayList<>();
        for (int r = 0; r < configs[nextState].length; r++) {
            for (int c = 0; c < configs[nextState][r].length; c++) {
                if (configs[nextState][r][c] == 1) {
                    int newRow = topLeftR + r;
                    int newCol = topLeftC + c;
                    // Cancel rotation if the new position is out-of-bounds (including negative indices)
                    if (newCol < 0 || newCol >= 10 || newRow < 0 || newRow >= 20) {
                        return false;
                    }
                    MiniBlock tempBlock = new MiniBlock(color, newRow, newCol, id);
                    if (GameManager.touching(Direction.DOWN, tempBlock) ||
                        GameManager.touching(Direction.LEFT, tempBlock) ||
                        GameManager.touching(Direction.RIGHT, tempBlock)) {
                        return false;
                    }
                    newComponents.add(tempBlock);
                }
            }
        }
        rotState = nextState;
        components = newComponents;
        return true;
    }

    public boolean moveLeft(GridPane pane) {
        // Calculate the left-most column among components.
        int minCol = Integer.MAX_VALUE;
        for (MiniBlock block : components) {
            minCol = Math.min(minCol, block.getCol());
        }
        // If moving left would result in a negative column, cancel the move.
        if (minCol - 1 < 0) {
            return false;
        }
        // Otherwise, update each MiniBlock.
        for (MiniBlock block : components) {
            block.moveLeft();
        }
        topLeftC--;
        return true;
    }
    
    public boolean moveRight(GridPane pane) {
        // Calculate the right-most column among components.
        int maxCol = 0;
        for (MiniBlock block : components) {
            maxCol = Math.max(maxCol, block.getCol());
        }
        // Assuming a grid width of 10 (indices 0–9). If moving right would exceed that, cancel.
        if (maxCol + 1 >= 10) {
            return false;
        }
        for (MiniBlock block : components) {
            block.moveRight();
        }
        topLeftC++;
        return true;
    }

    //getters and setters
    public void setTopLeftR(int topLeftR) {
        this.topLeftR = topLeftR;
    }

    public void setTopLeftC(int topLeftC) {
        this.topLeftC = topLeftC;
    }

    public int getTopLeftR() {
        return topLeftR;
    }

    public int getTopLeftC() {
        return topLeftC;
    }

    public ArrayList<MiniBlock> getComponents() {
        return components;
    }

    @Override
    public String toString() {
        return "Generic";
    }

}
