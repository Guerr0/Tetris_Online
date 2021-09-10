package client.logic;

import client.Client;
import client.logic.tetromino.Tetromino;
import client.logic.tetromino.TetrominoFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static client.logic.Cell.CellType.*;
import static client.logic.utils.MapUtils.createLinePointsMap;
import static client.logic.utils.MapUtils.createSpeedLevelMap;
import static client.logic.utils.MatrixUtils.createGameMatrix;

/**
 * Questa classe crea il campo da gioco (con le dimensioni indicate nel costruttore) e permette di modificare gli
 * aspetti principali del gioco, come il tetramino che sta cadendo (fallingTetromino) e quello in <i>hold</i>
 * (holdTetromino). Possiede anche un riferimento a {@link client.Client Client}, i cui metodi permettono di comunicare
 * con altri componenti (GUI, networking, ecc).
 */
public class Game {
    private final Client client;
    private final Cell[][] gameMatrix;
    private final TetrominoFactory tetrominoFactory;
    private Tetromino fallingTetromino;
    private Tetromino holdTetromino;
    private final int cols;
    private final int rows;
    private int score;
    private final HashMap<Integer, Integer> speedLevelMap;
    private final HashMap<Integer, Integer[]> clearLinePoints;
    private int level = 0;
    private final int LINES_FOR_LEVEL = 20;
    private int linesToNextLevel = LINES_FOR_LEVEL;

    /**
     * Il costruttore prende in input il numero di colonne e righe del campo da gioco e il riferimento a
     * {@link client.Client Client}.
     * <br>
     * Crea un tetramino in maniera randomica che sara' quello in caduta. Effettua anche una mappatura tra velocita' e
     * livello. Effettua una mappatura tra righe "cleared" e il relativo punteggio.
     * */
    public Game(int cols, int rows, Client client) {
        this.client = client;
        this.cols = cols;
        this.rows = rows;
        this.gameMatrix = createGameMatrix(cols, rows);
        this.speedLevelMap = createSpeedLevelMap();
        this.clearLinePoints = createLinePointsMap();

        this.tetrominoFactory = new TetrominoFactory();
        this.fallingTetromino = tetrominoFactory.createRandomTetromino();
    }


    /* GETTER */
    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public Cell[][] getGameMatrix() {
        return gameMatrix;
    }

    public Tetromino getFallingTetromino(){
        return fallingTetromino;
    }

    public Tetromino getHoldTetromino() {
        return holdTetromino;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getMillisecondsDelay(){
        return speedLevelMap.get(level);
    }

    public Client getClient() {
        return client;
    }

    /* SETTER */
    public void setHoldTetromino(Tetromino holdTetromino) {
        this.holdTetromino = holdTetromino;
    }

    public void setFallingTetromino(Tetromino tetromino){
        this.fallingTetromino = tetromino;
    }

    /**
     * Metodo protetto utilizzato da {@link GameAction GameAction} per "cementare" il blocco in caduta
     * al campo da gioco.
     * <br>
     * Successivamente crea un altro tetramino con la factory.
     * */
    protected void settleBlock(){
        Coordinate[] absoluteCoordinates = fallingTetromino.getAbsoluteCoordinates();
        for (Coordinate coordinate : absoluteCoordinates) {
            gameMatrix[coordinate.getX()][coordinate.getY()].setCellType(FULL);
            gameMatrix[coordinate.getX()][coordinate.getY()].setCellColor(fallingTetromino.getColor());
        }
        this.fallingTetromino = this.tetrominoFactory.createRandomTetromino();
        clearLineIfNeeded();
    }

    /* UTILITIES */
    public void enableColorShuffling(){
        tetrominoFactory.enableColorShuffle();
    }

    public void disableColorShuffling(){
        tetrominoFactory.disableColorShuffle();
    }

    private void nextLevelIfNeeded(){
        if(linesToNextLevel <= 0){
            if(level < speedLevelMap.size()) {
                level++;
                linesToNextLevel = LINES_FOR_LEVEL;
            }
        }
    }

    //unione dei due metodi successivi
    private void clearLineIfNeeded() {
        List<Integer> linesToClear = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            if(lineCanBeCleared(i)){
                linesToClear.add(i);
            }
        }
        for(int x : linesToClear){
            clearLine(x);
            linesToNextLevel--;
            nextLevelIfNeeded();
            //qualcosa per mandare il messaggio SEND JUNK LINE al giocatore
            //selezionato come target
        }
        if(linesToClear.size() != 0){
            addToScore(linesToClear.size());
        }
    }

    //controlla se linea passata a parametro può/deve essere pulita
    private boolean lineCanBeCleared(int line){
        int fullCounter = 0;

        for (int j = 0; j < cols; j++) {
            Cell.CellType type = gameMatrix[j][line].getCellType();
            if(type == FULL){
                fullCounter++;
            }
        }
        return fullCounter == cols;
    }

    //pulisce effettivamente la linea
    private void clearLine(int line){
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < line; j++) {
                gameMatrix[i][line].setCellType(EMPTY);
                if(gameMatrix[i][j].getCellType() == FULL){
                    if(j+1 < rows){
                        gameMatrix[i][j+1].setCellType(FULL);
                        gameMatrix[i][j].setCellType(EMPTY);
                    }
                }
            }
        }
    }

    private void addToScore(int lines) {
        int pointsToAdd = clearLinePoints.get(level)[lines-1];
        this.score += pointsToAdd;
    }

    /**
     * Questo metodo e' solo un tramite per non dover passare il Client a troppe classi. Si occupa di chiamare il
     * metodo {@link client.Client#quitGame() quitGame()} di Client, che si occupera' della chiusura dei thread etc...
     * */
    public void quitGame(){
        client.quitGame();
    }
}
