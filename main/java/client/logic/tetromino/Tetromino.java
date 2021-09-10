package client.logic.tetromino;

import client.logic.Cell.CellColor;
import client.logic.Coordinate;

import java.io.Serializable;

/**
 * Questa classe astratta viene ereditata dagli specifici tetramini (TetrominoI, TetrominoJ, ...), che vengono istanziati
 * dalla {@link TetrominoFactory factory}.
 * <br>
 * Possiede i metodi necessari per modificare le coordinate di origine, ruotare il tetramino e ottenere le coordinate
 * relative al campo da gioco.
 * */
public abstract class Tetromino implements Serializable {
    private final Coordinate[][] relativeCoordinates;
    private Coordinate originCoordinates;
    private int orientation;
    private final TetrominoType tetrominoType;

    public abstract CellColor getColor();

    public enum TetrominoType{
        BLOCK_I, BLOCK_J, BLOCK_L, BLOCK_O, BLOCK_S, BLOCK_T, BLOCK_Z
    }

    public Tetromino(TetrominoType tetrominoType, Coordinate[][] relativeCoordinates){
        this.tetrominoType = tetrominoType;
        this.relativeCoordinates = relativeCoordinates;
        this.orientation = 0;
        this.originCoordinates = new Coordinate(4,0);
    }

    /* GETTER */
    public final Coordinate getOriginCoordinates() {
        return originCoordinates;
    }

    public final TetrominoType getTetrominoType() {
        return tetrominoType;
    }

    /* SETTER */
    public final void setOriginCoordinates(Coordinate originCoordinates) {
        this.originCoordinates = originCoordinates;
    }

    public final void setOriginCoordinates(int x, int y){
        originCoordinates.setX(x);
        originCoordinates.setY(y);
    }

    /* UTILITIES */
    /**
     * Restituisce le coordinate relative al campo di gioco.
     * Per ogni singola coordinata relativa, aggiunge le coordinate dell'origine come offset.
     * <br>
     * Ad esempio, se le coordinate relative sono [0,1],[2,2],[2,3],[3,1] e l'origine [1,3], le coordinate assolute sono
     * [1,4],[3,5],[3,6],[4,4]
     * */
    public final Coordinate[] getAbsoluteCoordinates(){
        Coordinate[] absoluteCoordinateArray = createAbsoluteCoordinates();

        int xOrigin = originCoordinates.getX();
        int yOrigin = originCoordinates.getY();

        addOffset(absoluteCoordinateArray, xOrigin, yOrigin);

        return absoluteCoordinateArray;
    }

    /* Java (come C) tratta % come operatore per il resto, non modulo. Quindi il risultato del resto avra' lo stesso
     * segno del dividendo.
     * Abbiamo bisogno dell'operatore modulo, che in java e' presente nella libreria Math.
     * Per maggiori info:
     * https://stackoverflow.com/questions/43953268/mod-with-negative-numbers-gives-a-negative-result-in-java-and-c
     */

    /**
     * Questo metodo utilizza l'aritmetica modulare per ruotare il tetramino (quindi ciclare le sue 4 rotazioni) in
     * senso orario.
     * */
    public void rotateClockwise() {
        this.orientation = Math.floorMod(this.orientation + 1, 4); // equivalente (orientation+1) % 4
    }

    /**
     * Questo metodo utilizza l'aritmetica modulare per ruotare il tetramino (quindi ciclare le sue 4 rotazioni) in
     * senso antiorario.
     * */
    public void rotateCounterClockwise(){
        this.orientation = Math.floorMod(this.orientation - 1, 4); // equivalente (orientation-1) % 4
    }

    /**
     * Questo metodo ritorna una matrice bidimensionale di oggetti {@link Coordinate Coordinate}, necessari per le
     * coordinate relative.
     * Crea la matrice e successivamente la riempie di istanze di {@link Coordinate Coordinate}, se non si facesse cio'
     * si avrebbero dei puntatori a null.
     * */
    protected static Coordinate[][] createRelativeCoordinatesMatrix(){
        Coordinate[][] matrix = new Coordinate[4][4];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                matrix[x][y] = new Coordinate();
            }
        }
        return matrix;
    }

    /**
     * Questo metodo crea l'array di coordinate (in oggetti {@link Coordinate Coordinate}), che verra' poi popolato dalle
     * coordinate che {@link #addOffset(Coordinate[] absArray, int xOrigin, int yOrigin) addOffset()} calcolera'.
     * <br>
     * */
    private Coordinate[] createAbsoluteCoordinates() {
        Coordinate[] absoluteCoordinateArray = new Coordinate[4];
        for (int i = 0; i < 4; i++) {
            absoluteCoordinateArray[i] = new Coordinate();
        }
        return absoluteCoordinateArray;
    }

    /**
     * Questo metodo popola l'array delle coordinate assolute con le coordinate relative sommate all'offset.
     * */
    private void addOffset(Coordinate[] absArray, int xOrigin, int yOrigin) {
        for(int i = 0; i < 4; i++){
            Coordinate c = relativeCoordinates[orientation][i];
            absArray[i].setX(xOrigin + c.getX());
            absArray[i].setY(yOrigin + c.getY());
        }
    }
}
