package com.tetris.blocks;

import javafx.scene.paint.Color;

public class OBlock extends BlockBase {
        // fields
        private static int[][][] configs = {
                        {
                                        { 1, 1 },
                                        { 1, 1 },
                        },
                        {
                                        { 1, 1 },
                                        { 1, 1 },
                        },
                        {
                                        { 1, 1 },
                                        { 1, 1 },
                        },
                        {
                                        { 1, 1 },
                                        { 1, 1 },
                        },
        };
        private static Color color = Color.KHAKI; // replace with actual color

        // constructor
        public OBlock(int topLeftR, int topLeftC) {
                super(color, configs, topLeftR, topLeftC);
        }

        // methods
        @Override
        public String toString() {
                return "OBlock";
        }

}