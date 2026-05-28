package com.example.miniproyecto2sudokuedadlepr.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A utility class for creating and displaying standard JavaFX alerts.
 */
public class MessageBox implements com.example.miniproyecto2sudokuedadlepr.utilities.MessageBoxPort {
    private Alert dialog;

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayMessage(String headerText, String contentText, AlertType alertType) {
        dialog = new Alert(alertType);
        dialog.setTitle("Sudoku");
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);
        dialog.showAndWait();
    }
}