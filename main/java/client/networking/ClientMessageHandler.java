package client.networking;

import client.Client;
import client.gui.GUI;
import client.gui.windows.WelcomeWindow;
import client.gui.windows.WindowSwitcher;
import client.logic.Coordinate;
import server.networking.message.Message;
import server.networking.message.messages.GameStatusArrayMessage;
import server.networking.message.messages.PlayerNameMessage;

import java.io.IOException;

public class ClientMessageHandler {
    private final GUI gui;
    private final ClientThread clientThread;
    private final Client client;

    public ClientMessageHandler(GUI gui, ClientThread clientThread, Client client){
        this.gui = gui;
        this.clientThread = clientThread;
        this.client = client;
    }

    public void handleMessage(Message receivedMessage) throws InterruptedException {
        switch (receivedMessage.getMessageType()) {
            case START_GAME -> {
                System.out.println("start game ricevuto");
                client.startGame();
            }
            case END_GAME, QUIT -> client.quitGame();
            case PLAYER_NAME -> {
                PlayerNameMessage playerNameMessage = (PlayerNameMessage) receivedMessage;
                if(playerNameMessage.isNameAvailable()){
                    WelcomeWindow.usernameAvailable = true;
                    clientThread.setPlayerName(playerNameMessage.getPlayerNameProposal());
                } else {
                    WelcomeWindow.usernameAvailable = false;
                }
                synchronized (WelcomeWindow.usernameLock){
                    WelcomeWindow.usernameLock.notify();
                }
            }
            case GARBAGE_LINE -> System.out.println("è un RJL");
            case PAUSE -> client.pauseGame();
            case PING -> System.out.println("è un ping");
            case GAME_STATUS_ARRAY -> gui.setGameStatusArrayList(((GameStatusArrayMessage) receivedMessage).getGameStatusArrayList());
            case GAME_STATUS -> System.out.println("GAME_STATUS");
            case RESUME -> client.resumeGame();
        }
    }
}

