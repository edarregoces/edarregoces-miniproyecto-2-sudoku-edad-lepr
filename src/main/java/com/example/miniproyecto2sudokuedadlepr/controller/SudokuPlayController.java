package com.example.miniproyecto2sudokuedadlepr.controller;

import com.example.miniproyecto2sudokuedadlepr.model.game.HintMove;
import com.example.miniproyecto2sudokuedadlepr.model.game.PuzzleGame;
import com.example.miniproyecto2sudokuedadlepr.model.game.PuzzleGameBridge;
import com.example.miniproyecto2sudokuedadlepr.model.game.PuzzleGamePort;
import com.example.miniproyecto2sudokuedadlepr.model.user.PlayerData;
import com.example.miniproyecto2sudokuedadlepr.view.SudokuEndStage;
import com.example.miniproyecto2sudokuedadlepr.view.SudokuPlayStage;
import com.example.miniproyecto2sudokuedadlepr.view.SudokuStartStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for the main Sudoku game view (sudoku-game-view.fxml).
 * This class is responsible for initializing and managing the game board's UI.
 */
public class SudokuPlayController implements Initializable {

    /**
     * The GridPane element from the FXML file that holds the Sudoku board cells.
     */
    @FXML
    private GridPane puzzleGridPane;

    private PuzzleGamePort puzzleRound;
    private PlayerData playerData;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded. It creates a new game instance
     * and starts the game.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        puzzleRound = new PuzzleGameBridge(puzzleGridPane);
        puzzleRound.setRoundCompleteHandler(this::showCompletionScreen);
        puzzleRound.startRound();
    }

    @FXML
    void returnToMenu(ActionEvent event){
        try {
            SudokuStartStage.getOrCreateStage();
            SudokuPlayStage.closeStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void askForHint() {
        PuzzleGame.HintEngine hintEngine = puzzleRound.getHintEngine();

        // pedir sugerencia
        HintMove hintMove = hintEngine.findSafeHint();
        if (hintMove == null) {
            System.out.println("No hay sugerencias disponibles");
        } else {
            // aplicar a modelo (board) y bloquearla
            boolean applied = hintEngine.placeHintOnBoard(hintMove);
            if (applied) {
                // actualizar la vista para que muestre el número
                TextField cellField = puzzleRound.findCellField(hintMove.rowIndex(), hintMove.columnIndex()); // tu helper existente
                if (cellField != null) {
                    cellField.setText(String.valueOf(hintMove.hintValue()));
                    cellField.setStyle("-fx-text-fill: #ffb62d; -fx-font-weight: bold;");
                    cellField.setEditable(false); // opcional: si quieres que quede no editable
                }
                if (puzzleRound.isRoundSolved()) {
                    showCompletionScreen();
                }
            } else {
                System.out.println("La sugerencia ya no es aplicable. Intenta de nuevo.");
            }
        }

    }

    /**
     * Sets the user for the current game session. This method is called by the
     * welcome controller to pass the user's data.
     *
     * @param playerData The player object containing profile information, such as the playerName.
     */
    public void assignPlayer(PlayerData playerData) {
        this.playerData = playerData;
    }

    private void showCompletionScreen() {
        try {
            SudokuEndStage.getOrCreateStage();
            SudokuPlayStage.closeStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
