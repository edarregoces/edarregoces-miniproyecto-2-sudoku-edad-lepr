package com.example.sudoku2.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A utility class for creating and displaying standard JavaFX alerts.
 */
public class MessageBox implements com.example.sudoku2.utils.MessageBoxPort {
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