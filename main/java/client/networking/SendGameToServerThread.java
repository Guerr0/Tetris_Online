package client.networking;

import client.logic.Game;
import server.networking.message.Message;
import server.networking.message.messages.GameStatusMessage;

import java.io.IOException;

/*
thread che manda la classe Game al server
 */

public class SendGameToServerThread extends Thread {
    private boolean running;
    private Game game;
    private final ClientThread clientThread;

    public SendGameToServerThread(ClientThread clientThread){
        this.clientThread = clientThread;
        this.running = true;
    }

    public void quit(){
        this.running = false;
    }

    public void setGame(Game game){
        this.game = game;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        while(running){
            Message message = new GameStatusMessage(clientThread.getPlayerName(),
                    game.getGameMatrix(), game.getFallingTetromino());
            try {
                clientThread.sendMessage(message);
            } catch ( IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
