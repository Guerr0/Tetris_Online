package server.networking.message.messages;

import client.logic.Cell;
import client.logic.tetromino.Tetromino;
import server.networking.message.Message;

public class GameStatusMessage extends Message {
    private final Cell[][] gameMatrix;
    private final Tetromino fallingTetromino;

    public GameStatusMessage(String sender, Cell[][] gameMatrix, Tetromino fallingTetromino){
        super(MessageType.GAME_STATUS);
        this.gameMatrix = gameMatrix;
        this.fallingTetromino = fallingTetromino;
    }

    public Cell[][] getGameMatrix() {
        return gameMatrix;
    }

    public Tetromino getFallingTetromino() {
        return fallingTetromino;
    }
}
