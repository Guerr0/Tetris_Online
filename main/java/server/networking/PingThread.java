package server.networking;

import java.util.ArrayList;
import java.util.HashMap;

public class PingThread extends Thread {
    private boolean running;
    private ArrayList<ServerThread> serverThreads;
    private HashMap<ServerThread, Integer> pingCountersMap;

    public PingThread(ArrayList<ServerThread> serverThreads){
        this.serverThreads = serverThreads;
        initPingCountersMap();
    }

    public void start() {
        super.start();
        this.running = true;
    }

    public int getCounter() {
        int currentCount = 0;
        for(ServerThread serverThread : serverThreads){
            currentCount = pingCountersMap.get(serverThread);
            return currentCount;
        }
        return currentCount;
    }

    private void initPingCountersMap() {
        pingCountersMap = new HashMap<>();
        for(ServerThread serverThread : serverThreads){
            int MAX_RETRIES = 5;
            pingCountersMap.put(serverThread, MAX_RETRIES);
        }
    }

    private void decrementCounters() {
        for(ServerThread serverThread : serverThreads){
            System.out.println("sto controllando "+serverThread.isRunning());
            if(!serverThread.isRunning()){
                int currentCount = pingCountersMap.get(serverThread);
                System.out.println("PLAYER NAME: " + serverThread.getPlayerName() + "\tCOUNTER: " + currentCount);
                pingCountersMap.put(serverThread, currentCount - 1);
            }

        }
    }

    private boolean isCounterZero(ServerThread serverThread) {
        return pingCountersMap.get(serverThread) == 0;
    }

    private void checkAllCounters() {
        for(ServerThread serverThread : serverThreads){
            if(isCounterZero(serverThread)){
                //playerThread.quit();
                System.out.println("PLAYER " + serverThread.getPlayerName() + " EXCEEDED MAX RETRIES FOR PING");
                serverThreads.remove(serverThread); //è già la disconnessione del player?

            }
        }
    }

    @Override
    public void run() {
        while(running){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            decrementCounters();
            checkAllCounters();
        }
    }
}
