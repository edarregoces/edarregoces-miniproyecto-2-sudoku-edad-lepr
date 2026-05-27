package com.example.sudoku2.view;
import com.example.sudoku2.controller.SudokuPlayController;
import com.example.sudoku2.controller.SudokuStartController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A singleton Stage for the welcome window.
 * This class ensures that only one instance of the welcome window can exist.
 */
public class SudokuStartStage extends Stage {

    /**
     * Private constructor to enforce the singleton pattern. It loads the FXML view,
     * sets up the scene, and configures the stage properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    private SudokuStartStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/sudoku2/fxml/sudoku-welcome-view.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Sudoku");
        setResizable(false);
        getIcons().add(
                new Image(String.valueOf(getClass().getResource("/com/example/sudoku2/images/logo.png")))
        );
        show();
    }

    /**
     * Inner static class to hold the singleton instance (lazy initialization).
     */
    private static class Holder {
        private static SudokuStartStage INSTANCE = null;
    }

    /**
     * Provides global access to the singleton SudokuStartStage instance.
     * Creates the instance if it doesn't exist yet.
     *
     * @return The single instance of SudokuStartStage.
     * @throws IOException if the FXML file cannot be loaded during the first creation.
     */
    public static SudokuStartStage getOrCreateStage() throws IOException {
        Holder.INSTANCE = Holder.INSTANCE != null ?
                Holder.INSTANCE : new SudokuStartStage();
        return Holder.INSTANCE;
    }

    /**
     * Closes the stage, effectively deleting the instance from view.
     */
    public static void closeStage() {
        Holder.INSTANCE.close();
        Holder.INSTANCE = null;
    }
}
