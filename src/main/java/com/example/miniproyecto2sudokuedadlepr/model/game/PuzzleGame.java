package com.example.miniproyecto2sudokuedadlepr.model.game;

import com.example.miniproyecto2sudokuedadlepr.utilities.MessageBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.ERROR;

/**
 * Concrete implementation of the Sudoku game logic.
 */
public class PuzzleGame extends com.example.miniproyecto2sudokuedadlepr.model.game.PuzzleGameBase {
    private MessageBox messageBox;
    private Runnable roundCompleteHandler;
    private boolean roundCompleted;

    public PuzzleGame(GridPane puzzleGridPane) {
        super(puzzleGridPane);
    }

    @Override
    public void startRound() {
        messageBox = new MessageBox();
        roundCompleted = false;
        int size = puzzleBoard.getBoardSize();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int number = puzzleBoard.fetchCellValue(row, col);
                System.out.print(number + " ");

                TextField textField = findCellField(row, col);
                textField.setAlignment(Pos.CENTER);
                textField.setText(String.valueOf(number));

                if (number != 0) {
                    textField.setEditable(false);
                } else {
                    textField.setText("");
                }

                handleNumberField(textField, row, col);
            }
            System.out.println();
        }

        puzzleBoard.releaseOpenCells();
    }

    private void handleNumberField(TextField txt, int row, int col) {
        TextField textField = findCellField(row, col);
        txt.setOnKeyReleased(event -> {
            textField.setStyle("-fx-text-fill: #56b5c1");
            String input = txt.getText().trim();

            if (input.isEmpty()) {
                puzzleBoard.updateCellValue(row, col, 0);
                puzzleBoard.releaseCell(row, col);
                return;
            }

            if (!validateInput(input)) {
                puzzleBoard.updateCellValue(row, col, 0);
                puzzleBoard.releaseCell(row, col);
                textField.setText("");
                messageBox.displayMessage("Input error",
                        "Please enter a number between 1 and 6",
                        ERROR);
                return;
            }

            int number = Integer.parseInt(input);
            puzzleBoard.updateCellValue(row, col, 0);

            if (!puzzleBoard.acceptsPlacement(row, col, number)) {
                textField.setStyle("-fx-text-fill: red;");
                puzzleBoard.releaseCell(row, col);
                return;
            }

            puzzleBoard.updateCellValue(row, col, number);
            puzzleBoard.secureCell(row, col);
            notifyRoundCompleteIfSolved();
        });
    }

    @Override
    public boolean isRoundSolved() {
        return puzzleBoard.isCompletelySolved();
    }

    @Override
    public void setRoundCompleteHandler(Runnable roundCompleteHandler) {
        this.roundCompleteHandler = roundCompleteHandler;
    }

    private void notifyRoundCompleteIfSolved() {
        if (roundCompleted || !isRoundSolved()) {
            return;
        }

        roundCompleted = true;
        if (roundCompleteHandler != null) {
            roundCompleteHandler.run();
        }
    }

    private boolean validateInput(String input) {
        return input.matches("[1-6]");
    }

    @Override
    public TextField findCellField(int row, int col) {
        for (Node node : puzzleGridPane.getChildren()) {
            Integer r = GridPane.getRowIndex(node);
            Integer c = GridPane.getColumnIndex(node);

            int nodeRow = (r == null) ? 0 : r;
            int nodeCol = (c == null) ? 0 : c;

            if (nodeRow == row && nodeCol == col) {
                return (TextField) node;
            }
        }
        return null;
    }

    @Override
    public HintEngine getHintEngine() {
        return new HintEngine();
    }

    public class HintEngine implements com.example.miniproyecto2sudokuedadlepr.model.game.HintProvider {
        @Override
        public HintMove findSafeHint() {
            HintMove singleCandidate = findSingleCandidate();
            if (singleCandidate != null) return singleCandidate;

            HintMove hiddenSingle = findHiddenSingle();
            if (hiddenSingle != null) return hiddenSingle;

            return null;
        }

        private HintMove findSingleCandidate() {
            int size = puzzleBoard.getBoardSize();

            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (puzzleBoard.fetchCellValue(row, col) == 0 && !puzzleBoard.isCellSecured(row, col)) {
                        List<Integer> options = getPossibleNumbers(row, col);

                        if (options.size() == 1) {
                            return new HintMove(row, col, options.get(0));
                        }
                    }
                }
            }
            return null;
        }

        private HintMove findHiddenSingle() {
            int size = puzzleBoard.getBoardSize();

            for (int row = 0; row < size; row++) {
                for (int number = 1; number <= size; number++) {
                    int colCandidate = -1;
                    for (int col = 0; col < size; col++) {
                        if (puzzleBoard.fetchCellValue(row, col) == 0
                                && !puzzleBoard.isCellSecured(row, col)
                                && puzzleBoard.acceptsPlacement(row, col, number)) {
                            if (colCandidate != -1) {
                                colCandidate = -1;
                                break;
                            }
                            colCandidate = col;
                        }
                    }

                    if (colCandidate != -1) {
                        return new HintMove(row, colCandidate, number);
                    }
                }
            }

            for (int col = 0; col < size; col++) {
                for (int number = 1; number <= size; number++) {
                    int rowCandidate = -1;
                    for (int row = 0; row < size; row++) {
                        if (puzzleBoard.fetchCellValue(row, col) == 0
                                && !puzzleBoard.isCellSecured(row, col)
                                && puzzleBoard.acceptsPlacement(row, col, number)) {
                            if (rowCandidate != -1) {
                                rowCandidate = -1;
                                break;
                            }
                            rowCandidate = row;
                        }
                    }

                    if (rowCandidate != -1) {
                        return new HintMove(rowCandidate, col, number);
                    }
                }
            }

            int blockRows = 2;
            int blockCols = 3;
            for (int blockRow = 0; blockRow < size; blockRow += blockRows) {
                for (int blockCol = 0; blockCol < size; blockCol += blockCols) {
                    for (int number = 1; number <= size; number++) {
                        int rowCandidate = -1;
                        int colCandidate = -1;

                        for (int row = blockRow; row < blockRow + blockRows; row++) {
                            for (int col = blockCol; col < blockCol + blockCols; col++) {
                                if (puzzleBoard.fetchCellValue(row, col) == 0
                                        && !puzzleBoard.isCellSecured(row, col)
                                        && puzzleBoard.acceptsPlacement(row, col, number)) {
                                    if (rowCandidate != -1) {
                                        rowCandidate = -1;
                                        colCandidate = -1;
                                        break;
                                    }
                                    rowCandidate = row;
                                    colCandidate = col;
                                }
                            }

                            if (rowCandidate == -1) {
                                break;
                            }
                        }

                        if (rowCandidate != -1 && colCandidate != -1) {
                            return new HintMove(rowCandidate, colCandidate, number);
                        }
                    }
                }
            }

            return null;
        }

        @Override
        public boolean placeHintOnBoard(HintMove suggestion) {
            if (suggestion == null) {
                return false;
            }

            int row = suggestion.rowIndex();
            int col = suggestion.columnIndex();
            int number = suggestion.hintValue();

            if (puzzleBoard.fetchCellValue(row, col) != 0) return false;
            if (puzzleBoard.isCellSecured(row, col)) return false;
            if (!puzzleBoard.acceptsPlacement(row, col, number)) return false;

            puzzleBoard.updateCellValue(row, col, number);
            puzzleBoard.secureCell(row, col);
            return true;
        }

        private List<Integer> getPossibleNumbers(int row, int col) {
            List<Integer> numbers = new ArrayList<>();
            for (int number = 1; number <= puzzleBoard.getBoardSize(); number++) {
                if (puzzleBoard.acceptsPlacement(row, col, number)) {
                    numbers.add(number);
                }
            }
            return numbers;
        }
    }
}
