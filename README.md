# HU-Load Game

## Overview
HU-Load is a mining game inspired by Motherload, developed using Java 8 and JavaFX. The objective is to drill underground, collect valuable minerals and gems, and accumulate as much money as possible before the machine runs out of fuel.

## Game Mechanics

### Elements
- **Drilling Machine:** Has a fuel tank, storage, and money case. Controlled using arrow keys.
- **Underground Elements:**
  - **Soil:** Can be drilled but yields no money or weight.
  - **Valuables (Minerals & Gems):** Can be collected for money but add weight.
  - **Boulders:** Cannot be drilled and form the underground borders.
  - **Lava:** Destroys the machine upon contact, causing game over.

### Rules
- The game must include at least three types of valuables.
- Soil should be the most common element on the screen.
- Boulders must form the underground borders (except the top).
- The machine always consumes a small amount of fuel, but significantly more while drilling.
- Running out of fuel or drilling into lava results in a game over.
- The machine falls due to gravity when no element is underneath it.
- The machine can only fly in empty spaces and cannot drill upwards.

## Project Structure
```
- <project_root>
  - src/
    - Cell.java
    - DrillerMachine.java
    - Ground.java
    - Lava.java
    - Main.java
    - Obstacle.java
    - Soil.java
    - Valuable.java
  - assets/
  - Checklist.pdf
```

## How to Run
### Compilation
```sh
javac8 *.java  # or javac8 Main.java
```
### Execution
```sh
java8 Main
```

## Important Notes
- JavaFX are used; Swing, AWT, and FXML are not used.

## References
- [JavaFX Documentation](https://docs.oracle.com/javase/8/javafx/api/)

