package client;

import client.gui.GUI;
import client.gui.windows.WindowSwitcher;
import client.logic.Game;
import client.logic.GameHandler;
import client.networking.ClientThread;
import client.networking.SendGameToServerThread;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    private GUI gui;
    private static ClientThread clientThread;
    private Game game;
    private GameHandler gameHandler;
    private boolean running;
    private final boolean debug;
    private GameLoopThread gameLoopThread;
    private SendGameToServerThread sendGameToServerThread;

    public Client(boolean debug) {
        this.debug = debug;
    }

    public boolean isRunning() {
        return running;
    }

    public void /*GUI*/ initGUI() throws IOException {
        this.gui = new GUI(60);
    }

    public void /*Networking*/ initNetworking(){
        Scanner scan = new Scanner(System.in);
        System.out.println("inserisci nome giocatore per connetterti");
        String playerName = scan.next();

        this.clientThread = new ClientThread(this);
        clientThread.setPlayerName(playerName);
        clientThread.setGUI(gui);
        clientThread.start();
    }

    public void startGame() throws InterruptedException {
        this.game = new Game(12, 24, this);
        this.gameHandler = new GameHandler(game, this);
        this.gui.setGame(game);
        this.gui.setGameHandler(gameHandler);
        this.gameLoopThread = new GameLoopThread(debug, game, gameHandler);
        this.gameLoopThread.start();
        this.sendGameToServerThread = new SendGameToServerThread(clientThread);
        this.sendGameToServerThread.setGame(game);
        //this.sendGameToServerThread.start();
        this.gui.getWindowSwitcher().switchWindow(WindowSwitcher.WindowType.GAME);
    }

    public void quitGame() {
        // Segnala alla GUI che stai quittando
        // Segnala al server che stai quittando
        this.gameLoopThread.quit();
    }

    public void pauseGame(){
        this.gameLoopThread.quit();
    }

    public void resumeGame(){
        this.gameLoopThread.start();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Client client = new Client(false);
        client.initGUI();
        client.initNetworking();
        //client.gui.getWindowSwitcher().switchWindow(WindowSwitcher.WindowType.WELCOME);
    }

    public static ClientThread getClientThread() {
        return clientThread;
    }
}
