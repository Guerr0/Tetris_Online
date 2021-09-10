package server.networking;

import client.logic.Cell;
import client.logic.tetromino.Tetromino;
import server.networking.message.Message;
import server.networking.message.MessageDecoder;
import server.networking.message.ServerMessageHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;


/**
 * Classe che funge da connessione lato server con un client, generata dal server
 * non appena è richiesta una connessione da un giocatore.
 */

public class ServerThread extends Thread {
    private boolean running;
    private String playerName;
    private final Socket socket;
    private ObjectOutputStream outgoingStream;
    private ObjectInputStream incomingStream;
    private MessageDecoder messageDecoder;
    private ServerMessageHandler serverMessageHandler;
    private Cell[][] gameMatrix;
    private Tetromino fallingTetromino;

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        init();
    }

    @Override
    public void run(){
        try {
            while(running){
                readMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readMessage() throws IOException, ClassNotFoundException {
        if (socket.isConnected()) {
            Message receivedMessage = messageDecoder.decodeMessage(incomingStream);
            System.out.println(receivedMessage.getTimestamp() + "\t" + receivedMessage.getMessageType());
            serverMessageHandler.handleMessage(receivedMessage);
        } else {
            throw new SocketException();
        }
    }

    public void sendMessage(Message message) throws IOException {
        if (socket.isConnected()) {
            outgoingStream.reset();
            outgoingStream.writeObject(message);

        }
    }

    public void endConnection() throws IOException {
        this.socket.close();
        this.running = false;
    }

    public boolean isRunning() {
        return running;
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

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


    private void init() throws IOException {
        outgoingStream = new ObjectOutputStream(socket.getOutputStream());
        incomingStream = new ObjectInputStream(socket.getInputStream());
        messageDecoder = new MessageDecoder();
        serverMessageHandler = new ServerMessageHandler(this);
        running = true;
    }
}



