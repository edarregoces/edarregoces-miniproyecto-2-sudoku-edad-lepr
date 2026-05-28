package com.example.miniproyecto2sudokuedadlepr.model.game;

import javafx.scene.control.TextField;

/**
 * Defines the contract for a game class. Any class that implements
 * this interface must provide a method to start the game.
 */
public interface PuzzleGamePort {
    /**
     * Initializes and starts the game logic.
     */
    void startRound();
    TextField findCellField(int row, int col);
    PuzzleGame.HintEngine getHintEngine();
    boolean isRoundSolved();
    void setRoundCompleteHandler(Runnable roundCompleteHandler);
}
