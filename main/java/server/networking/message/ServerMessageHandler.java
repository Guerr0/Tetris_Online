package server.networking.message;

import server.Server;
import server.networking.ServerThread;
import server.networking.message.messages.GameStatusArrayMessage;
import server.networking.message.messages.GameStatusMessage;
import server.networking.message.messages.GarbageLineMessage;
import server.networking.message.messages.PlayerNameMessage;

import java.io.IOException;
import java.util.ArrayList;

public class ServerMessageHandler {
    private final ServerThread serverThread;

    public ServerMessageHandler(ServerThread serverThread) {
        this.serverThread = serverThread;
    }

    public void handleMessage(Message receivedMessage) {
        ArrayList<ServerThread> serverThreadArrayList = Server.getServerThreads();

        switch (receivedMessage.getMessageType()) {
            case START_GAME:
                System.out.println("è un start");
                break;
            case END_GAME:
                System.out.println("è un end");
                break;
            case PLAYER_NAME:
                String name = ((PlayerNameMessage) receivedMessage).getPlayerNameProposal();
                boolean isNameAvailable = true;
                if(serverThreadArrayList != null){
                    synchronized (serverThreadArrayList){
                        for (ServerThread serverThread : serverThreadArrayList) {
                            if (name.equals(serverThread.getPlayerName())) {
                                isNameAvailable = false;
                                break;
                            }
                        }
                        if(isNameAvailable) serverThread.setPlayerName(name);
                        Message confirmMessage = new PlayerNameMessage(isNameAvailable, name);
                        try {
                            serverThread.sendMessage(confirmMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case GARBAGE_LINE:
                synchronized (serverThreadArrayList){
                    String target = ((GarbageLineMessage) receivedMessage).getTarget();
                    for (ServerThread serverThread : serverThreadArrayList) {
                        if (serverThread.getPlayerName().equals(target)) {
                            Message msg = new GarbageLineMessage(this.serverThread.getPlayerName(), target);
                            try {
                                serverThread.sendMessage(msg);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                break;
            case PAUSE:
                System.out.println("è un pause");
                break;
            case PING:
                break;
            case GAME_STATUS_ARRAY:
                ((GameStatusArrayMessage) receivedMessage).getGameStatusArrayList();
            case GAME_STATUS:
                //System.out.println("E' GAME STATUS");
                assert receivedMessage instanceof GameStatusMessage;
                serverThread.setFallingTetromino(((GameStatusMessage) receivedMessage).getFallingTetromino());
                serverThread.setGameMatrix(((GameStatusMessage) receivedMessage).getGameMatrix());
                break;
            case QUIT:
                break;
            case RESUME:
                break;

        }
    }
}
