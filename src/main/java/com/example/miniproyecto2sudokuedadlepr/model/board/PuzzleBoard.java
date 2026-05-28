package com.example.miniproyecto2sudokuedadlepr.model.board;

import java.util.*;

/**
 * Generates and validates a 6x6 Sudoku board divided into 2x3 blocks.
 * The board is stored as a tree: root node -> row nodes -> cell nodes.
 */
public class PuzzleBoard implements PuzzleBoardPort {
    private final int SIZE = 6;
    private final int BLOCK_ROWS = 2;
    private final int BLOCK_COLS = 3;

    private final int TOTAL_BLOCK_ROWS = SIZE / BLOCK_ROWS;
    private final int TOTAL_BLOCK_COLS = SIZE / BLOCK_COLS;
    private final int TOTAL_BLOCKS = TOTAL_BLOCK_ROWS * TOTAL_BLOCK_COLS;

    private final PuzzleBoardTree puzzleTree;
    private final Set<String> lockedCells = new HashSet<>();
    private final Random random = new Random();

    private final int MAX_GENERATION_ATTEMPTS = 2000;

    public PuzzleBoard() {
        puzzleTree = new PuzzleBoardTree(SIZE);
        refreshBoard();
    }

    @Override
    public void refreshBoard() {
        int attempts = 0;
        boolean success = false;

        while (attempts < MAX_GENERATION_ATTEMPTS && !success) {
            attempts++;
            clearBoard();
            lockedCells.clear();

            if (!seedTwoCellsPerBlock()) {
                continue;
            }

            markInitialLockedCells();

            if (hasSingleSolution()) {
                success = true;
            }
        }

        if (!success) {
            System.out.println("Warning: couldn't generate unique-solution board in " + MAX_GENERATION_ATTEMPTS + " attempts. Using last generated board.");
        }
    }

    public boolean seedTwoCellsPerBlock() {
        for (int blockIndex = 0; blockIndex < TOTAL_BLOCKS; blockIndex++) {
            int blockRow = blockIndex / TOTAL_BLOCK_COLS;
            int blockCol = blockIndex % TOTAL_BLOCK_COLS;

            int startRow = blockRow * BLOCK_ROWS;
            int startCol = blockCol * BLOCK_COLS;

            int filled = 0;
            int innerAttempts = 0;
            int maxInnerAttempts = 200;

            while (filled < 2 && innerAttempts < maxInnerAttempts) {
                innerAttempts++;

                int row = startRow + random.nextInt(BLOCK_ROWS);
                int col = startCol + random.nextInt(BLOCK_COLS);

                if (fetchCellValue(row, col) != 0) {
                    continue;
                }

                List<Integer> numbers = new ArrayList<>();
                for (int number = 1; number <= SIZE; number++) {
                    numbers.add(number);
                }
                Collections.shuffle(numbers, random);

                for (int number : numbers) {
                    if (acceptsPlacement(row, col, number)) {
                        updateCellValue(row, col, number);
                        filled++;
                        break;
                    }
                }
            }

            if (filled < 2) {
                return false;
            }
        }
        return true;
    }

    private void markInitialLockedCells() {
        lockedCells.clear();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (fetchCellValue(row, col) != 0) {
                    lockedCells.add(key(row, col));
                }
            }
        }
    }

    @Override
    public boolean acceptsPlacement(int row, int col, int candidate) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return false;
        }

        if (candidate < 1 || candidate > SIZE) {
            return false;
        }

        for (int currentCol = 0; currentCol < SIZE; currentCol++) {
            if (currentCol != col && fetchCellValue(row, currentCol) == candidate) {
                return false;
            }
        }

        for (int currentRow = 0; currentRow < SIZE; currentRow++) {
            if (currentRow != row && fetchCellValue(currentRow, col) == candidate) {
                return false;
            }
        }

        int startRow = (row / BLOCK_ROWS) * BLOCK_ROWS;
        int startCol = (col / BLOCK_COLS) * BLOCK_COLS;

        for (int currentRow = startRow; currentRow < startRow + BLOCK_ROWS; currentRow++) {
            for (int currentCol = startCol; currentCol < startCol + BLOCK_COLS; currentCol++) {
                if ((currentRow != row || currentCol != col)
                        && fetchCellValue(currentRow, currentCol) == candidate) {
                    return false;
                }
            }
        }

        return true;
    }

    private String key(int row, int col) {
        return row + "," + col;
    }

    @Override
    public void secureCell(int row, int col) {
        lockedCells.add(key(row, col));
    }

    @Override
    public void releaseCell(int row, int col) {
        lockedCells.remove(key(row, col));
    }

    @Override
    public boolean isCellSecured(int row, int col) {
        return lockedCells.contains(key(row, col));
    }

    @Override
    public void releaseOpenCells() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (fetchCellValue(row, col) == 0) {
                    releaseCell(row, col);
                }
            }
        }
    }

    @Override
    public void clearBoard() {
        puzzleTree.clear();
        lockedCells.clear();
    }

    @Override
    public boolean hasSingleSolution() {
        SolutionCounter counter = new SolutionCounter();
        solveAndCount(counter);
        return counter.value == 1;
    }

    @Override
    public boolean isCompletelySolved() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int value = fetchCellValue(row, col);
                if (value == 0 || !acceptsPlacement(row, col, value)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean solveAndCount(SolutionCounter counter) {
        if (counter.value > 1) {
            return true;
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (fetchCellValue(row, col) == 0) {
                    for (int number = 1; number <= SIZE; number++) {
                        if (acceptsPlacement(row, col, number)) {
                            updateCellValue(row, col, number);
                            boolean stop = solveAndCount(counter);
                            updateCellValue(row, col, 0);

                            if (stop) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            }
        }

        counter.value++;
        return counter.value > 1;
    }

    @Override
    public int getBoardSize() {
        return SIZE;
    }

    @Override
    public int fetchCellValue(int row, int col) {
        return puzzleTree.fetchValue(row, col);
    }

    @Override
    public void updateCellValue(int row, int col, int value) {
        puzzleTree.updateValue(row, col, value);
    }

    @Override
    public PuzzleBoardTree getPuzzleTree() {
        return puzzleTree;
    }

    private static class SolutionCounter {
        private int value;
    }
}
