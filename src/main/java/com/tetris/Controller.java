package com.tetris;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;

import com.tetris.blocks.BlockBase;

public class Controller {

    private static boolean isMoving = false;

    // Updated key handler: remove the block parameter and retrieve the current block dynamically.
    public static void setupInputHandlers(Scene scene, GridPane pane) {
        scene.setOnKeyPressed(e -> {
            if (isMoving) return;
            // Get the current block dynamically.
            BlockBase currentBlock = GameManager.getCurrentBlock();
            if (currentBlock == null) return; // If no block is active, ignore input.
            switch (e.getCode()) {
                case W, UP -> {
                    if (GameManager.rotate(currentBlock, pane)) {
                        isMoving = true;
                        new Timeline(new KeyFrame(Duration.millis(50), event -> isMoving = false)).play();
                    }
                }
                case A, LEFT -> {
                    isMoving = true;
                    GameManager.moveL(currentBlock, pane);
                    new Timeline(new KeyFrame(Duration.millis(50), event -> isMoving = false)).play();
                }
                case D, RIGHT -> {
                    isMoving = true;
                    GameManager.moveR(currentBlock, pane);
                    new Timeline(new KeyFrame(Duration.millis(50), event -> isMoving = false)).play();
                }
                case S, DOWN -> {
                    isMoving = true;
                    currentBlock.moveDown(pane);
                    new Timeline(new KeyFrame(Duration.millis(50), event -> isMoving = false)).play();
                }
                case SPACE -> {
                    // Hard drop using Timeline
                    int[] movedRows = {0};
                    final Timeline[] timelineHolder = new Timeline[1];
                    timelineHolder[0] = new Timeline(new KeyFrame(Duration.millis(10), event -> {
                        if (currentBlock.moveDown(pane)) {
                            movedRows[0]++;
                        } else {
                            timelineHolder[0].stop();
                            GameManager.addToScore(movedRows[0] * 2);
                            onBlockStop(scene, currentBlock, pane);
                        }
                    }));
                    timelineHolder[0].setCycleCount(Timeline.INDEFINITE);
                    timelineHolder[0].play();
                }
            }
        });
    }

    public static void onBlockStop(Scene scene, BlockBase block, GridPane pane) {
        // Add each component to the permanent list
        for (MiniBlock bl : block.getComponents()) {
            if (!GameManager.getListOfBlocks().contains(bl)) {
                GameManager.getListOfBlocks().add(bl);
            }
        }
        
        // Process filled rows and update score
        ArrayList<Integer> filledRows = GameManager.checkFilledRows(pane);
        int linesCleared = filledRows.size();
        if (linesCleared > 0) {
            if (linesCleared == 1)
                GameManager.addToScore(40);
            else if (linesCleared == 2)
                GameManager.addToScore(100);
            else if (linesCleared == 3)
                GameManager.addToScore(300);
            else if (linesCleared >= 4)
                GameManager.addToScore(1200);
        }
        
        GameManager.moveDownAll(filledRows, pane);
        GameManager.setCurrentBlock(GameManager.generateBlock());
        GameManager.addBlock(GameManager.getCurrentBlock(), pane);
    }
}