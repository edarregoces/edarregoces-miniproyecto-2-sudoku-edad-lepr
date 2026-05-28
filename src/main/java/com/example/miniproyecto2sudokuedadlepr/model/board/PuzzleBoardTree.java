package com.example.miniproyecto2sudokuedadlepr.model.board;

/**
 * Node-based tree representation for a Sudoku board.
 * Root node -> row nodes -> cell nodes.
 */
public class PuzzleBoardTree {
    private final PuzzleNode treeRoot;
    private final int dimension;

    public PuzzleBoardTree(int dimension) {
        this.dimension = dimension;
        this.treeRoot = new PuzzleNode(-1, -1, 0);

        for (int row = 0; row < dimension; row++) {
            PuzzleNode rowNode = new PuzzleNode(row, -1, 0);
            treeRoot.attachChild(rowNode);

            for (int col = 0; col < dimension; col++) {
                rowNode.attachChild(new PuzzleNode(row, col, 0));
            }
        }
    }

    public int dimension() {
        return dimension;
    }

    public PuzzleNode rootNode() {
        return treeRoot;
    }

    public int fetchValue(int row, int col) {
        return locateNode(row, col).fetchValue();
    }

    public void updateValue(int row, int col, int value) {
        locateNode(row, col).updateValue(value);
    }

    public void clear() {
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                updateValue(row, col, 0);
            }
        }
    }

    public PuzzleNode locateNode(int row, int col) {
        validateIndex(row);
        validateIndex(col);
        return getChildAt(getChildAt(treeRoot, row), col);
    }

    private PuzzleNode getChildAt(PuzzleNode parent, int index) {
        PuzzleNode current = parent.firstChild;
        int currentIndex = 0;

        while (current != null && currentIndex < index) {
            current = current.nextSibling;
            currentIndex++;
        }

        if (current == null) {
            throw new IndexOutOfBoundsException("Tree child index out of range: " + index);
        }

        return current;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= dimension) {
            throw new IndexOutOfBoundsException("PuzzleBoard index out of range: " + index);
        }
    }

    public static class PuzzleNode {
        private final int row;
        private final int col;
        private int value;
        private PuzzleNode firstChild;
        private PuzzleNode nextSibling;

        private PuzzleNode(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        private void attachChild(PuzzleNode child) {
            if (firstChild == null) {
                firstChild = child;
                return;
            }

            PuzzleNode current = firstChild;
            while (current.nextSibling != null) {
                current = current.nextSibling;
            }
            current.nextSibling = child;
        }

        public int rowIndex() {
            return row;
        }

        public int columnIndex() {
            return col;
        }

        public int fetchValue() {
            return value;
        }

        public void updateValue(int value) {
            this.value = value;
        }

        public PuzzleNode firstChildNode() {
            return firstChild;
        }

        public PuzzleNode nextSiblingNode() {
            return nextSibling;
        }
    }
}
