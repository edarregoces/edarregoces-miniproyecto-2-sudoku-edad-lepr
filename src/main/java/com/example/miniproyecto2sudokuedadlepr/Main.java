package com.example.miniproyecto2sudokuedadlepr;

import com.example.miniproyecto2sudokuedadlepr.view.SudokuStartStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        SudokuStartStage.getOrCreateStage();
    }
}
