package server.networking;

import server.networking.message.GameStatus;
import server.networking.message.Message;
import server.networking.message.messages.GameStatusArrayMessage;

import java.io.IOException;
import java.util.ArrayList;

public class SendPlayerListThread extends Thread {
    private final ArrayList<ServerThread> serverThreadArrayList;
    private boolean running;

    public SendPlayerListThread(ArrayList<ServerThread> serverThreadArrayList){
        this.serverThreadArrayList = serverThreadArrayList;
        this.running = true;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        while(running){
            if(serverThreadArrayList != null) {
                try {
                    synchronized (serverThreadArrayList) {
                        for (ServerThread serverThread : serverThreadArrayList) {
                            if (serverThread != null) {
                                String playerName = serverThread.getPlayerName();
                                ArrayList<GameStatus> gameStatusArrayList = new ArrayList<>(3);
                                for (ServerThread serverThreadInner : serverThreadArrayList) {
                                    if (!serverThreadInner.getPlayerName().equals(playerName)) {
                                        GameStatus gameStatus = new GameStatus();
                                        gameStatus.setPlayer(playerName);
                                        gameStatus.setGameMatrix(serverThreadInner.getGameMatrix());
                                        gameStatus.setFallingTetromino(serverThreadInner.getFallingTetromino());
                                        gameStatusArrayList.add(gameStatus);
                                    }
                                }
                                Message message = new GameStatusArrayMessage(gameStatusArrayList);
                                serverThread.sendMessage(message);
                                Thread.sleep(500);
                            }
                        }
                    }

                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void quit(){
        this.running = false;
    }
}
