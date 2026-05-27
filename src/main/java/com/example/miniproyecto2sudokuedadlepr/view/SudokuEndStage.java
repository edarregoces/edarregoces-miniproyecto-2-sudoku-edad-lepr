package com.example.sudoku2.view;

import com.example.sudoku2.controller.SudokuEndController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the final stage (window) of the Sudoku application.
 * <p>
 * This class loads and displays the "Final" screen defined in the FXML file
 * {@code sudoku-final-view.fxml}. It follows the Singleton design pattern
 * to ensure that only one instance of this stage exists at any given time.
 */
public class SudokuEndStage extends Stage {
    /** Controller associated with this stage's FXML view. */
    private SudokuEndController controller;

    /**
     * Private constructor that initializes the final stage by loading the FXML layout,
     * setting up the scene, window title, icon, and other properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    private SudokuEndStage() throws IOException {
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("/com/example/sudoku2/fxml/sudoku-final-view.fxml"));
        Parent root = Loader.load();
        controller = Loader.getController();

        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Game Complete");
        setResizable(false);
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/sudoku2/images/logo.png"))));
        show();
    }

    /**
     * Holder class for the Singleton instance.
     * This ensures lazy initialization and thread safety.
     */
    private static class Holder {
        private static SudokuEndStage INSTANCE = null;
    }

    /**
     * Returns the single instance of {@code SudokuEndStage}.
     * If no instance exists yet, a new one will be created.
     *
     * @return the single instance of {@code SudokuEndStage}.
     * @throws IOException if the FXML file cannot be loaded when creating the instance.
     */
    public static SudokuEndStage getOrCreateStage() throws IOException {
        SudokuEndStage.Holder.INSTANCE = SudokuEndStage.Holder.INSTANCE != null ?
                SudokuEndStage.Holder.INSTANCE : new SudokuEndStage();
        return SudokuEndStage.Holder.INSTANCE;
    }

    /**
     * Returns the controller associated with this stage.
     *
     * @return the {@link SudokuEndController} managing this view.
     */
    public SudokuEndController getController() {
        return controller;
    }

    /**
     * Deletes the current instance of the stage by closing it
     * and setting the Singleton reference to {@code null}.
     * <p>
     * This allows the stage to be re-created later if needed.
     */
    public static void closeStage() {
        SudokuEndStage.Holder.INSTANCE.close();
        SudokuEndStage.Holder.INSTANCE = null;
    }
}
