package server.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingThread extends Thread {
    private boolean running;
    private final int PORT = 3111;
    private ExecutorService threadPool;
    private ServerSocket serverSocket;
    private ArrayList<ServerThread> serverThreads;
    private PingThread pingThread;

    public NetworkingThread() throws IOException {
        init();
    }

    @Override
    public void run() {
        while(running){
            try {
                acceptConnections();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void acceptConnections() throws IOException {
        System.out.println("In attesa di connessioni..");
        Socket socket = serverSocket.accept();
        System.out.println("connesso con un giocatore client");
        //ServerThread serverThread = new ServerThread(socket);
        //serverThreads.add(serverThread);
        //threadPool.execute(serverThread);
    }

    public void quit(){
        this.running = false;
    }

    public void init() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.serverThreads = new ArrayList<>();
        this.pingThread = new PingThread(serverThreads);
        this.threadPool = Executors.newFixedThreadPool(4);
    }
}
