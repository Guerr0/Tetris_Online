package server.networking.message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import client.logic.Cell;
import client.logic.tetromino.Tetromino;

/**
 * Classe Message. Ogni messaggio è composto dai campi:
 * sender: mittente del messaggio
 * commandType: tipo di messaggio inviato
 * target: questo campo sarà riempito solo se il messaggio è di tipo SEND_JUNK_LINES
 * e in questo caso conterrà il giocatore vittima della linea spazzatura.
 * In tutti gli altri casi questo campo sarà null.
 */

public abstract class Message implements Serializable {
    public enum MessageType {
        START_GAME, END_GAME, PLAYER_NAME, GARBAGE_LINE, PAUSE, PING, GAME_STATUS_ARRAY, GAME_STATUS, QUIT, RESUME,
    }

    private final MessageType messageType;
    private final LocalDateTime timestamp;

    public Message(MessageType messageType){
        this.timestamp = LocalDateTime.now();
        this.messageType = messageType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
