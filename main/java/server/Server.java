package server;


import server.networking.PingThread;
import server.networking.SendPlayerListThread;
import server.networking.ServerCLIThread;
import server.networking.ServerThread;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Classe Server che gestisce il Server del gioco a cui dovranno connettersi i clients
 */

public class Server {
    public static final int PORT = 3111;
    private final boolean debug;
    private static final ArrayList<ServerThread> serverThreads = new ArrayList<>(4);
    private PingThread pingThread;
    private SendPlayerListThread sendPlayerListThread;
    private AcceptConnectionThread acceptConnectionThread;
    public static final int MAX_PLAYERS = 4;

    public Server(boolean debug){
        this.debug = debug;
    }

    public void /*Networking*/ initNetworking() throws Exception {
        //this.pingThread = new PingThread(serverThreads);
        //this.pingThread.start();
        this.sendPlayerListThread = new SendPlayerListThread(serverThreads);
        this.sendPlayerListThread.start();
        this.acceptConnectionThread = new AcceptConnectionThread(serverThreads, MAX_PLAYERS);
        this.acceptConnectionThread.start();
    }

    public void initCommandThread(){
        ServerCLIThread serverCLIThread = new ServerCLIThread();
        serverCLIThread.start();
    }


    public static void main(String[] args) throws Exception {
        Server server = new Server(false);
        server.initNetworking();
        server.initCommandThread();
    }

    public static ArrayList<ServerThread> getServerThreads() {
        return serverThreads;
    }
}
/*
TODO
meccanismo di ping da attivare

 */
