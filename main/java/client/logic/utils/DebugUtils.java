package client.logic.utils;

import client.logic.Cell;
import client.logic.Coordinate;
import client.logic.tetromino.Tetromino;

public class DebugUtils {
    public static void printGameMatrix(Cell[][] gameMatrix){
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 12; j++) {
                String type = determineCellType(gameMatrix[j][i].getCellType());
                System.out.print(type + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printGameStatus(Cell[][] gameMatrix, Tetromino fallingTetromino){
        int[][] tetrominoCoordinatesArray = getCoordinates(fallingTetromino);
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 12; j++) {
                String type = "e";
                for (int[] coord : tetrominoCoordinatesArray) {
                    if (coord[0] == j && coord[1] == i) {
                        type = "#";
                        break;
                    } else {
                        type = determineCellType(gameMatrix[j][i].getCellType());
                    }
                }
                System.out.print(type + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int[][] getCoordinates(Tetromino fallingTetromino) {
        Coordinate[] absoluteCoordinate = fallingTetromino.getAbsoluteCoordinates();
        int[][] absoluteCoordinateArray = new int[4][2];
        for (int i = 0; i < absoluteCoordinate.length; i++) {
            absoluteCoordinateArray[i][0] = absoluteCoordinate[i].getX();
            absoluteCoordinateArray[i][1] = absoluteCoordinate[i].getY();
        }
        return absoluteCoordinateArray;
    }

    public static void printGameMatrixWithIndexes(Cell[][] gameMatrix){
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 12; j++) {
                String type = determineCellType(gameMatrix[j][i].getCellType());
                System.out.print("[" + j + ";" + i + "]\t" + type + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String determineCellType(Cell.CellType type){
        switch (type){
            case FULL:
                return "@";
            case JUNK:
                return "#";
            case EMPTY:
                return ".";
            case GHOST:
                return "*";
            default:
                return "";
        }
    }

    public static void printFallingTetrominoCoordinates(Tetromino fallingTetromino){
        for(Coordinate coord : fallingTetromino.getAbsoluteCoordinates()){
            System.out.print("[" + coord.getX() + ";" + coord.getY() + "]\t");
        }
        System.out.println();
    }
}
