package com.tetris.blocks;

import javafx.scene.paint.Color;

public class JBlockL extends BlockBase {
        // fields
        private static int[][][] configs = {
                        {
                                        { 1, 0, 0 },
                                        { 1, 1, 1 },
                                        { 0, 0, 0 },
                        },
                        {
                                        { 0, 1, 1 },
                                        { 0, 1, 0 },
                                        { 0, 1, 0 },
                        },
                        {
                                        { 0, 0, 0 },
                                        { 1, 1, 1 },
                                        { 0, 0, 1 },
                        },
                        {
                                        { 0, 1, 0 },
                                        { 0, 1, 0 },
                                        { 1, 1, 0 },
                        }
        };
        private static Color color = Color.ROYALBLUE; // replace with actual color

        // constructor
        public JBlockL(int topLeftR, int topLeftC) {
                super(color, configs, topLeftR, topLeftC);
        }

        // methods
        @Override
        public String toString() {
                return "JBlockL";
        }

}