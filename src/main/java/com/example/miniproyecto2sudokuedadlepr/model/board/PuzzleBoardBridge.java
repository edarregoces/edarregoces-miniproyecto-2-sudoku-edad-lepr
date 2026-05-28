package com.example.miniproyecto2sudokuedadlepr.model.board;

/**
 * Adaptador para usar la clase concreta PuzzleBoard
 * detrás de la interfaz PuzzleBoardPort
 */
public class PuzzleBoardBridge implements PuzzleBoardPort {

    private final PuzzleBoard delegatedBoard;

    public PuzzleBoardBridge() {
        this.delegatedBoard = new PuzzleBoard();
    }

    @Override
    public boolean acceptsPlacement(int row, int col, int candidate) {
        return delegatedBoard.acceptsPlacement(row, col, candidate);
    }

    @Override
    public void refreshBoard() {
        delegatedBoard.refreshBoard();
    }

    @Override
    public void secureCell(int row, int col) {
        delegatedBoard.secureCell(row, col);
    }

    @Override
    public void releaseCell(int row, int col) {
        delegatedBoard.releaseCell(row, col);
    }

    @Override
    public boolean isCellSecured(int row, int col) {
        return delegatedBoard.isCellSecured(row, col);
    }

    @Override
    public void releaseOpenCells() {
        delegatedBoard.releaseOpenCells();
    }

    @Override
    public void clearBoard() {
        delegatedBoard.clearBoard();
    }

    @Override
    public boolean hasSingleSolution() {
        return delegatedBoard.hasSingleSolution();
    }

    @Override
    public boolean isCompletelySolved() {
        return delegatedBoard.isCompletelySolved();
    }

    @Override
    public int getBoardSize() {
        return delegatedBoard.getBoardSize();
    }

    @Override
    public int fetchCellValue(int row, int col) {
        return delegatedBoard.fetchCellValue(row, col);
    }

    @Override
    public void updateCellValue(int row, int col, int value) {
        delegatedBoard.updateCellValue(row, col, value);
    }

    @Override
    public PuzzleBoardTree getPuzzleTree() {
        return delegatedBoard.getPuzzleTree();
    }
}
