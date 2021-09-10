package server;

import server.networking.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AcceptConnectionThread extends Thread {
    public static final int PORT = 3111;
    private boolean running;
    private final ArrayList<ServerThread> serverThreads;
    private final int MAX_PLAYERS;
    private int playerCount;

    public AcceptConnectionThread(ArrayList<ServerThread> serverThreads, int MAX_PLAYERS){
        this.serverThreads = serverThreads;
        this.MAX_PLAYERS = MAX_PLAYERS;
        this.running = true;
    }

    @Override
    public void run() {
        super.run();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(running) {
            try {
                if(playerCount >= MAX_PLAYERS)
                {
                    running = false;
                    break;
                }
                System.out.println("In attesa di connessioni..");
                assert serverSocket != null;
                Socket socket = serverSocket.accept();
                playerCount++;
                System.out.println("connesso con un giocatore client");
                ServerThread serverThread = new ServerThread(socket);
                synchronized (serverThreads){
                    serverThreads.add(serverThread);
                }
                serverThread.start();
                System.out.println(serverThreads.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
