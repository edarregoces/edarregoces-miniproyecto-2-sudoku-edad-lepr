package com.example.miniproyecto2sudokuedadlepr.model.game;

public interface HintProvider {
    /**
     * Retorna una sugerencia en formato {fila, columna, número}
     * o null si no hay sugerencias válidas.
     */
    HintMove findSafeHint();

    /**
     * Aplica la sugerencia al tablero si es válida.
     */
    boolean placeHintOnBoard(HintMove suggestion);
}
