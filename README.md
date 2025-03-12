# Javatrix

[![Java](https://img.shields.io/badge/Language-Java-ED8B00.svg?style=flat)](https://www.java.com)  
[![NCSSM](https://img.shields.io/badge/Education-NCSSM-107895.svg?style=flat)](https://www.ncssm.edu)

## Project Overview

**Javatrix** is a modernized Tetris clone written in Java. Originally developed as a senior project for the **Advanced Java** course in 2022 with my peer **Ian** (who graduated from NCSSM in 2023), the initial codebase was an early, unfinished version based on outdated Java practices. In 2025, while I was a college student at [NC State University](https://www.ncsu.edu), I revisited the project to update it to a modern version of Java, complete the implementation, fix bugs, and add new features for an enhanced gameplay experience.

### Game Features
- **Classic Tetris Gameplay** with modern enhancements
- **Soft Drop & Hard Drop** mechanics
- **Rotation & Collision Detection** for all block types
- **Score Tracking & Line Clearing**
- **Dynamic Input Handling** using JavaFX animations
- **Responsive GUI** built on JavaFX's GridPane

## Technical Implementation

### Architecture Overview
- **Core Engine**  
  - **BlockBase & MiniBlock:** Core classes representing Tetris pieces and their individual cells.
  - **GameManager:** Centralized game logic handling block generation, movement, collision detection, line clearing, score updates, and game over conditions.
  - **Controller:** Manages user input and integrates with JavaFX's event system to ensure smooth gameplay.

- **Graphics & Animation**  
  - Utilizes JavaFX's `GridPane` for rendering the game grid.
  - Uses `Timeline` and `AnimationTimer` for handling animations and game updates.
  - Provides real-time visual feedback with dynamic updates.

### Key Components
1. **BlockBase & MiniBlock**  
   - Define various Tetris block shapes and behaviors.
   - Handle rotation, movement, and collision checking with frozen blocks.
2. **GameManager**  
   - Manages overall game state including score, block locking, line clearing, and game over detection.
   - Implements block generation logic, including spawning pieces partially off-screen.
3. **Controller**  
   - Sets up keyboard input with throttling to avoid rapid-fire commands.
   - Maps key events to actions like moving left/right, rotating, and dropping blocks.

## Development Context

- **Initial Project (2022):**  
  Created as a senior project for the **Advanced Java** course with my peer **Ian** (NCSSM, Class of 2023). The original version was an early, incomplete prototype using outdated Java techniques.

- **Modernization & Enhancements (2025):**  
  While studying at NC State University, I revisited the project to:
  - Update the codebase to a modern version of Java.
  - Complete the implementation and resolve bugs.
  - Introduce new features and refine gameplay for a polished, portfolio-worthy project.

## How to Run

1. **Prerequisites:**  
   - Java 11 or newer installed on your system.
   - An IDE (e.g., IntelliJ IDEA, Eclipse, etc) or the command line.

2. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/Javatrix.git
   cd Javatrix

3. **Run the Game:**
   - Open the project in your preferred IDE and run the `GameManager` class, or compile and run via the command line:
   ```bash
   javac -d bin src/com/tetris/*.java src/com/tetris/blocks/*.java
   java -cp bin com.tetris.GameManager

## License

The MIT License (MIT)

Copyright (c) 2025 Turner Klapheke

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
