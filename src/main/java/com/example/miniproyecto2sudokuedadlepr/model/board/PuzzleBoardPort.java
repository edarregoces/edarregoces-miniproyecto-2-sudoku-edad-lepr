package com.example.miniproyecto2sudokuedadlepr.model.board;

/**
 * Defines the contract for a Sudoku board. Implementations must provide
 * methods for filling blocks and validating number placements.
 */
public interface PuzzleBoardPort {
    boolean acceptsPlacement(int row, int col, int candidate);
    void refreshBoard();
    void secureCell(int row, int col);
    void releaseCell(int row, int col);
    boolean isCellSecured(int row, int col);
    void releaseOpenCells();
    void clearBoard();
    boolean hasSingleSolution();
    boolean isCompletelySolved();
    int getBoardSize();
    int fetchCellValue(int row, int col);
    void updateCellValue(int row, int col, int value);
    PuzzleBoardTree getPuzzleTree();
}
