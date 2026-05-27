package com.example.sudoku2.view;
import com.example.sudoku2.controller.SudokuGuideController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the help stage (window) of the Sudoku application.
 * <p>
 * This class loads and displays the "Help" screen defined in the FXML file
 * {@code sudoku-help-view.fxml}. It implements the Singleton design pattern
 * to ensure that only one instance of this stage is created at a time.
 */
public class SudokuGuideStage extends Stage {

    /** Controller associated with this stage’s FXML view. */
    private SudokuGuideController controller;

    /**
     * Private constructor that initializes the help stage by loading the FXML layout,
     * setting up the scene, window title, icon, and other properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    private SudokuGuideStage() throws IOException {
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("/com/example/sudoku2/fxml/sudoku-help-view.fxml"));
        Parent root = Loader.load();
        controller = Loader.getController();

        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Help");
        setResizable(false);
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/sudoku2/images/logo.png"))));
        show();
    }

    /**
     * Holder class for the Singleton instance.
     * This pattern ensures lazy initialization and thread safety.
     */
    private static class Holder {
        private static SudokuGuideStage INSTANCE = null;
    }

    /**
     * Returns the single instance of {@code SudokuGuideStage}.
     * If no instance exists, a new one will be created.
     *
     * @return the single instance of {@code SudokuGuideStage}.
     * @throws IOException if the FXML file cannot be loaded when creating the instance.
     */
    public static SudokuGuideStage getOrCreateStage() throws IOException {
        SudokuGuideStage.Holder.INSTANCE = SudokuGuideStage.Holder.INSTANCE != null ?
                SudokuGuideStage.Holder.INSTANCE : new SudokuGuideStage();
        return SudokuGuideStage.Holder.INSTANCE;
    }

    /**
     * Returns the controller associated with this stage.
     *
     * @return the {@link SudokuGuideController} managing this view.
     */
    public SudokuGuideController getController() {
        return controller;
    }

    /**
     * Deletes the current instance of the help stage by closing it
     * and setting the Singleton reference to {@code null}.
     * <p>
     * This allows the stage to be re-created later if needed.
     */
    public static void closeStage() {
        SudokuGuideStage.Holder.INSTANCE.close();
        SudokuGuideStage.Holder.INSTANCE = null;
    }
}
