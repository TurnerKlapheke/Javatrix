package com.tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.scene.layout.CornerRadii;
import com.tetris.blocks.BlockBase;

public class GameManager extends Application {

    // functional variables
    private long previousTick;
    private static ArrayList<MiniBlock> listOfBlocks = new ArrayList<>();
    private static int score = 0;
    private static BlockBase currentBlock;

    // variables for making stuff look fancy
    private static final Background blackBackground = new Background(new BackgroundFill(Color.BLACK, null, null));
    private static final Background buttonBackground = new Background(
            new BackgroundFill(Color.rgb(82, 255, 140), new CornerRadii(10), Insets.EMPTY));
    private static final Font textFont = new Font("Calibri", 20);
    private static final Font titleFont = new Font("Impact", 40);

    @Override
    public void start(Stage primaryStage) {

        // START AND END SCENES --------------------------------------------------
        // Start scene
        Label gameTitle = new Label("TETRIS");
        gameTitle.setFont(titleFont);
        gameTitle.setTextFill(Color.rgb(82, 255, 140));

        Button startButton = new Button("Press to start");
        startButton.setBackground(buttonBackground);
        startButton.setFont(textFont);

        VBox startVBox = new VBox(10);
        startVBox.getChildren().addAll(gameTitle, startButton);
        startVBox.setAlignment(Pos.CENTER);
        startVBox.setBackground(blackBackground);

        // End scene
        Label endLabel = new Label("Your score: " + getScore());
        endLabel.setFont(textFont);
        endLabel.setTextFill(Color.rgb(82, 255, 140));

        Button startAgainButton = new Button("Play again");
        Button quitButton = new Button("Quit");
        startAgainButton.setBackground(buttonBackground);
        quitButton.setBackground(buttonBackground);
        startAgainButton.setFont(textFont);
        quitButton.setFont(textFont);

        VBox endVBox = new VBox(10);
        endVBox.getChildren().addAll(endLabel, startAgainButton, quitButton);
        endVBox.setAlignment(Pos.CENTER);
        endVBox.setBackground(blackBackground);

        // ACTUAL TETRIS ---------------------------------------------------------

        // Tilepane formatting
        GridPane pane = new GridPane();
        pane.setHgap(2);
        pane.setVgap(2);
        pane.setAlignment(Pos.CENTER);
        pane.setBackground(blackBackground);
        pane.setGridLinesVisible(true); // for now set visible for debugging

        // set the width and height of columns and rows
        // Set up 10 columns
        for (int i = 0; i < 10; i++) {
            pane.getColumnConstraints().add(new ColumnConstraints(30));
        }
        // Set up 20 rows
        for (int j = 0; j < 20; j++) {
            pane.getRowConstraints().add(new RowConstraints(30));
        }

        // scene!!!!!!!!!!
        Scene scene = new Scene(startVBox, 320, 640);
        primaryStage.setTitle("TETRIS");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Install input handlers once (the handler will retrieve the current block dynamically).
        Controller.setupInputHandlers(scene, pane);

        // animation timer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // repeat every 0.001 seconds
                if (now - previousTick > 1E9) {
                    previousTick = now;
                    // If there's no current block, do nothing.
                    if (GameManager.getCurrentBlock() == null) return;
                    if (!GameManager.getCurrentBlock().moveDown(pane)) {
                        if (checkGameOver()) {
                            endLabel.setText("Your score: " + getScore());
                            scene.setRoot(endVBox);
                            return;
                        }
                        Controller.onBlockStop(scene, GameManager.getCurrentBlock(), pane);
                    }
                    // Also check game over after moving:
                    if (checkGameOver()) {
                        endLabel.setText("Your score: " + getScore());
                        scene.setRoot(endVBox);
                    }
                }
            }
        };

        // set actions for transitioning
        startButton.setOnAction(e -> {
            GameManager.setCurrentBlock(GameManager.generateBlock());
            GameManager.addBlock(GameManager.getCurrentBlock(), pane);
            scene.setRoot(pane);
            timer.start();
        });

        // Restart and Quit actions
        startAgainButton.setOnAction(e -> {
            // Clear board
            for (int i = GameManager.getListOfBlocks().size() - 1; i >= 0; i--) {
                pane.getChildren().remove(GameManager.getListOfBlocks().get(i));
            }
            GameManager.getListOfBlocks().clear();
            score = 0;
            GameManager.setCurrentBlock(GameManager.generateBlock());
            GameManager.addBlock(GameManager.getCurrentBlock(), pane);
            scene.setRoot(pane);
            timer.start();
        });

        quitButton.setOnAction(e -> System.exit(0));
    }

    public static void addBlock(BlockBase block, GridPane pane) {
        // First remove any existing visuals for this block.
        removeBlockVisuals(block, pane);
        
        int totalRows = pane.getRowConstraints().size();
        int totalCols = pane.getColumnConstraints().size();
        for (MiniBlock bl : block.getComponents()) {
            if (bl.getRow() >= 0 && bl.getRow() < totalRows &&
                bl.getCol() >= 0 && bl.getCol() < totalCols) {
                if (!pane.getChildren().contains(bl)) {
                    pane.add(bl, bl.getCol(), bl.getRow());
                }
                if (!listOfBlocks.contains(bl)) {
                    listOfBlocks.add(bl);
                }
            }
        }
    }

    public static boolean rotate(BlockBase block, GridPane pane) {
        removeBlockVisuals(block, pane);
        boolean rotated = block.rotate();
        if (rotated) {
             addBlock(block, pane);
        }
        return rotated;
    }

    public static void refreshBlock(BlockBase block, GridPane pane) {
        int totalRows = pane.getRowConstraints().size();
        int totalCols = pane.getColumnConstraints().size();
        for (MiniBlock bl : block.getComponents()) {
            if (bl.getRow() >= 0 && bl.getRow() < totalRows &&
                bl.getCol() >= 0 && bl.getCol() < totalCols) {
                bl.updatePosition(pane);
            }
            if (!listOfBlocks.contains(bl)) {
                listOfBlocks.add(bl);
            }
        }
    }

    public static boolean moveL(BlockBase block, GridPane pane) {
        removeBlockVisuals(block, pane);
        boolean moved = block.moveLeft(pane);
        if (moved) {
             addBlock(block, pane);
        }
        return moved;
    }
    
    public static boolean moveR(BlockBase block, GridPane pane) {
        removeBlockVisuals(block, pane);
        boolean moved = block.moveRight(pane);
        if (moved) {
             addBlock(block, pane);
        }
        return moved;
    }

    public static Boolean touching(Direction direction, MiniBlock block) {
        // Add check for frozen blocks
        for (MiniBlock other : listOfBlocks) {
            if (other.getID() != block.getID() && 
                other.getRow() == block.getRow() + direction.rowOffset &&
                other.getCol() == block.getCol() + direction.colOffset) {
                return true;
            }
        }

        switch (direction) {
            case DOWN:
                if (block.getRow() >= 19) {
                    return true;
                } else {
                    int row = block.getRow() + 1;
                    int column = block.getCol();
                    for (MiniBlock otherBlock : listOfBlocks) {
                        if (otherBlock.getCol() == column && otherBlock.getRow() == row) {
                            if (otherBlock.getID() == block.getID()) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            case LEFT:
                if (block.getCol() <= 0) {
                    return true;
                } else {
                    int row = block.getRow();
                    int column = block.getCol() - 1;
                    for (MiniBlock otherBlock : listOfBlocks) {
                        if (otherBlock.getCol() == column && otherBlock.getRow() == row) {
                            if (otherBlock.getID() == block.getID()) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            case RIGHT:
                if (block.getCol() >= 9) {
                    return true;
                } else {
                    int row = block.getRow();
                    int column = block.getCol() + 1;
                    for (MiniBlock otherBlock : listOfBlocks) {
                        if (otherBlock.getCol() == column && otherBlock.getRow() == row) {
                            if (otherBlock.getID() == block.getID()) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            case UP:
                if (block.getRow() <= 0) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }


    public static boolean isEmpty(int c, int r, GridPane pane) {
        for (MiniBlock otherBlock : listOfBlocks) {
            if (otherBlock.getCol() == c && otherBlock.getRow() == r) {
                return false;
            }
        }
        return true;
    }

    // should call only after block is "locked in" but before new block spawns.
    // returns an array of the row indices that are filled.
    public static ArrayList<Integer> checkFilledRows(GridPane pane) {
        // go through every row
        ArrayList<Integer> filledRows = new ArrayList<>();
        int totalRows = pane.getRowConstraints().size();
        int totalCols = pane.getColumnConstraints().size();
        for (int i = 0; i < totalRows; i++) {
            boolean filledCompletely = true;
            for (int j = 0; j < totalCols; j++) {
                if (isEmpty(j, i, pane)) {
                    filledCompletely = false;
                    break;
                }
            }
            if (filledCompletely) {
                filledRows.add(i);
            }
        }
        return filledRows;
    }

    public static void moveDownAll(ArrayList<Integer> filledRows, GridPane pane) {
        if (filledRows.isEmpty()) return;
    
        // Remove blocks in filled rows
        ArrayList<MiniBlock> blocksToRemove = new ArrayList<>();
        for (MiniBlock block : listOfBlocks) {
            if (filledRows.contains(block.getRow())) {
                blocksToRemove.add(block);
            }
        }
        listOfBlocks.removeAll(blocksToRemove);
        pane.getChildren().removeAll(blocksToRemove);
        
        // Shift remaining blocks with bounds check
        int totalRows = pane.getRowConstraints().size();
        for (MiniBlock block : listOfBlocks) {
            int shift = (int) filledRows.stream().filter(row -> block.getRow() < row).count();
            if (shift > 0) {
                pane.getChildren().remove(block);
                block.setRow(block.getRow() + shift);
                // Ensure new row is valid before adding
                if (block.getRow() >= 0 && block.getRow() < totalRows) {
                    if (!pane.getChildren().contains(block)) {
                        pane.add(block, block.getCol(), block.getRow());
                    }
                }
            }
        }
    }

    public static ArrayList<MiniBlock> getListOfBlocks() {
        return listOfBlocks;
    }

    public static void setCurrentBlock(BlockBase bl) {
        currentBlock = bl;
    }

    public static BlockBase getCurrentBlock() {
        return currentBlock;
    }

    public void updateScore(int n) {
        if (n == 1) {
            score += 40;
        } else if (n == 2) {
            score += 100;
        } else if (n == 3) {
            score += 300;
        } else if (n == 4) {
            score += 1200;
        }
    }

    public static boolean checkGameOver() {
        // Game over if any locked block is in the top visible row (row 0)
        for (MiniBlock block : listOfBlocks) {
            if (block.getRow() == 0) {
                return true;
            }
        }
        return false;
    }

    public static BlockBase generateBlock() {
        int randNum = (int) (Math.random() * 7);
        if (randNum >= 6) {
            return new com.tetris.blocks.IBlock(-1, 3);  // Changed from -1 to 0
        } else if (randNum >= 5) {
            return new com.tetris.blocks.OBlock(0, 4);
        } else if (randNum >= 4) {
            return new com.tetris.blocks.TBlock(0, 3);
        } else if (randNum >= 3) {
            return new com.tetris.blocks.ZBlockR(0, 3);
        } else if (randNum >= 2) {
            return new com.tetris.blocks.ZBlockL(0, 3);
        } else if (randNum >= 1) {
            return new com.tetris.blocks.JBlockL(0, 3);
        } else {
            return new com.tetris.blocks.JBlockR(0, 3);
        }
    }

    public static void addToScore(int points) {
        score += points;
    }

    public static int getScore() {
        return score;
    }

    public static void removeBlockVisuals(BlockBase block, GridPane pane) {
        // Remove from both list and pane atomically
        ArrayList<MiniBlock> toRemove = new ArrayList<>(block.getComponents());
        pane.getChildren().removeAll(toRemove);
        listOfBlocks.removeAll(toRemove);
    }

    public static boolean canPlaceBlock(BlockBase block, GridPane pane) {
        int totalRows = pane.getRowConstraints().size();
        int totalCols = pane.getColumnConstraints().size();
        for (MiniBlock mb : block.getComponents()) {
            // Check only cells that are within the visible grid.
            if (mb.getRow() >= 0 && mb.getRow() < totalRows &&
                mb.getCol() >= 0 && mb.getCol() < totalCols) {
                for (MiniBlock locked : listOfBlocks) {
                    if (locked.getRow() == mb.getRow() && locked.getCol() == mb.getCol()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}