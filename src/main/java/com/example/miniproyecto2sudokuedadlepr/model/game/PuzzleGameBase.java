package com.example.miniproyecto2sudokuedadlepr.model.game;

import com.example.miniproyecto2sudokuedadlepr.model.board.PuzzleBoardBridge;
import com.example.miniproyecto2sudokuedadlepr.model.board.PuzzleBoardPort;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * An abstract base class for game logic, providing common properties
 * for a Sudoku game.
 */
public class PuzzleGameBase implements com.example.miniproyecto2sudokuedadlepr.model.game.PuzzleGamePort {
    /**
     * The UI grid where the board is displayed.
     */
    protected GridPane puzzleGridPane;
    /**
     * The underlying data structure and logic for the Sudoku board.
     */
    protected PuzzleBoardPort puzzleBoard;
    /**
     * A list of TextFields representing the cells on the board.
     */
    protected ArrayList<TextField> digitFields;

    /**
     * Constructs a PuzzleGameBase instance, initializing the board and UI components.
     *
     * @param puzzleGridPane The GridPane that will contain the Sudoku cells.
     */
    public PuzzleGameBase(GridPane puzzleGridPane) {
        this.puzzleGridPane = puzzleGridPane;
        this.puzzleBoard = new PuzzleBoardBridge();
        this.digitFields = new ArrayList<TextField>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startRound() {
    }

    @Override
    public TextField findCellField(int row, int col) {
        return null;
    }

    @Override
    public PuzzleGame.HintEngine getHintEngine() {
        return null;
    }

    @Override
    public boolean isRoundSolved() {
        return false;
    }

    @Override
    public void setRoundCompleteHandler(Runnable roundCompleteHandler) {
    }

}
