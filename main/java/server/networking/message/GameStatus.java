package server.networking.message;

import client.logic.Cell;
import client.logic.tetromino.Tetromino;

import java.io.Serializable;

public class GameStatus implements Serializable {
    private Cell[][] gameMatrix;
    private Tetromino fallingTetromino;
    private String player;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Cell[][] getGameMatrix() {
        return gameMatrix;
    }

    public void setGameMatrix(Cell[][] gameMatrix) {
        this.gameMatrix = gameMatrix;
    }

    public Tetromino getFallingTetromino() {
        return fallingTetromino;
    }

    public void setFallingTetromino(Tetromino fallingTetromino) {
        this.fallingTetromino = fallingTetromino;
    }
}
