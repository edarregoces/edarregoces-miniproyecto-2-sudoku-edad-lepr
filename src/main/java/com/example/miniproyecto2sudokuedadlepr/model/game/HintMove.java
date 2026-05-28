package com.example.miniproyecto2sudokuedadlepr.model.game;

public class HintMove {
    private final int row;
    private final int col;
    private final int number;

    public HintMove(int row, int col, int number) {
        this.row = row;
        this.col = col;
        this.number = number;
    }

    public int rowIndex() {
        return row;
    }

    public int columnIndex() {
        return col;
    }

    public int hintValue() {
        return number;
    }
}
