package com.example.sudoku2.view;

import com.example.sudoku2.controller.SudokuPlayController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A singleton Stage for the main Sudoku game window.
 * This class ensures that only one instance of the game window can exist.
 */
public class SudokuPlayStage extends Stage {
    private SudokuPlayController controller;

    /**
     * Private constructor to enforce the singleton pattern. It loads the FXML view,
     * sets up the scene, and configures the stage properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    private SudokuPlayStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/sudoku2/fxml/sudoku-game-view.fxml")
        );
        Parent root = loader.load();
        controller = loader.getController();

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
     * Returns the controller associated with this stage's view.
     *
     * @return The SudokuPlayController instance.
     */
    public SudokuPlayController getController() {
        return controller;
    }

    /**
     * Inner static class to hold the singleton instance (lazy initialization).
     */
    private static class Holder {
        private static SudokuPlayStage INSTANCE = null;
    }

    /**
     * Provides global access to the singleton SudokuPlayStage instance.
     * Creates the instance if it doesn't exist yet.
     *
     * @return The single instance of SudokuPlayStage.
     * @throws IOException if the FXML file cannot be loaded during the first creation.
     */
    public static SudokuPlayStage getOrCreateStage() throws IOException {
        SudokuPlayStage.Holder.INSTANCE = SudokuPlayStage.Holder.INSTANCE != null ?
                SudokuPlayStage.Holder.INSTANCE : new SudokuPlayStage();
        return SudokuPlayStage.Holder.INSTANCE;
    }

    /**
     * Closes the stage, effectively deleting the instance from view.
     */
    public static void closeStage() {
        SudokuPlayStage.Holder.INSTANCE.close();
        Holder.INSTANCE = null;
    }
}
