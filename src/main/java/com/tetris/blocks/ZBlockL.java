package com.tetris.blocks;

import javafx.scene.paint.Color;

public class ZBlockL extends BlockBase {
        // fields
        private static int[][][] configs = {
                        {
                                        { 1, 1, 0 },
                                        { 0, 1, 1 },
                                        { 0, 0, 0 },
                        },
                        {
                                        { 0, 0, 1 },
                                        { 0, 1, 1 },
                                        { 0, 1, 0 },
                        },
                        {
                                        { 0, 0, 0 },
                                        { 1, 1, 0 },
                                        { 0, 1, 1 },
                        },
                        {
                                        { 0, 1, 0 },
                                        { 1, 1, 0 },
                                        { 1, 0, 0 },
                        }
        };
        private static Color color = Color.FIREBRICK; // replace with actual color

        // constructor
        public ZBlockL(int topLeftR, int topLeftC) {
                super(color, configs, topLeftR, topLeftC);
        }

        // methods
        @Override
        public String toString() {
                return "ZBlockL";
        }

}