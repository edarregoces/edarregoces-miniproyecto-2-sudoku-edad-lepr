package com.example.miniproyecto2sudokuedadlepr.model.game;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Adapter class that implements the {@link com.example.miniproyecto2sudokuedadlepr.model.game.PuzzleGamePort} interface
 * and delegates all functionality to an instance of {@link PuzzleGame}.
 * <p>
 * This class serves as a bridge between the {@code PuzzleGamePort} interface
 * and the concrete {@code PuzzleGame} implementation, allowing the use
 * of {@code PuzzleGame} objects where an {@code PuzzleGamePort} reference is expected.
 */
public class PuzzleGameBridge implements com.example.miniproyecto2sudokuedadlepr.model.game.PuzzleGamePort {

    /** The underlying PuzzleGame instance to which operations are delegated. */
    private final PuzzleGame delegatedGame;

    /**
     * Constructs a new {@code PuzzleGameBridge} that wraps a {@link PuzzleGame} instance.
     *
     * @param puzzleGridPane the {@link GridPane} representing the Sudoku board in the UI.
     */
    public PuzzleGameBridge(GridPane puzzleGridPane) {
        this.delegatedGame = new PuzzleGame(puzzleGridPane);
    }

    /**
     * Starts the Sudoku game.
     * <p>
     * Delegates the call to {@link PuzzleGame#startRound()}.
     */
    @Override
    public void startRound() {
        delegatedGame.startRound();
    }

    /**
     * Returns the {@link TextField} at the specified row and column of the Sudoku board.
     * <p>
     * Delegates the call to {@link PuzzleGame#findCellField(int, int)}.
     *
     * @param row the row index of the desired cell.
     * @param col the column index of the desired cell.
     * @return the {@link TextField} corresponding to the given position.
     */
    @Override
    public TextField findCellField(int row, int col) {
        return delegatedGame.findCellField(row, col);
    }

    /**
     * Returns the {@link PuzzleGame.HintEngine} associated with this game.
     * <p>
     * Delegates the call to {@link PuzzleGame#getHintEngine()}.
     *
     * @return the {@link PuzzleGame.HintEngine} instance.
     */
    @Override
    public com.example.miniproyecto2sudokuedadlepr.model.game.PuzzleGame.HintEngine getHintEngine() {
        return delegatedGame.getHintEngine();
    }

    @Override
    public boolean isRoundSolved() {
        return delegatedGame.isRoundSolved();
    }

    @Override
    public void setRoundCompleteHandler(Runnable roundCompleteHandler) {
        delegatedGame.setRoundCompleteHandler(roundCompleteHandler);
    }
}
