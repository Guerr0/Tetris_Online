package client.logic.utils;

import client.logic.Cell;

public class MatrixUtils {
    /**
     * Questo metodo ritorna una matrice bidimensionale di oggetti {@link Cell Cell}, necessari per la matrice di gioco
     * (gameMatrix).
     * Crea la matrice e successivamente la riempie di istanze di {@link Cell Cell}, se non si facesse cio' si avrebbero
     * dei puntatori a null.
     * */
    public static Cell[][] createGameMatrix(int cols, int rows){
        Cell[][] matrix = new Cell[cols][rows];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                matrix[x][y] = new Cell();
            }
        }
        return matrix;
    }
}
