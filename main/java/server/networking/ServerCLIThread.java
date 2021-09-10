package server.networking;

import server.Server;
import server.networking.message.Message;
import server.networking.message.messages.PauseMessage;
import server.networking.message.messages.QuitMessage;
import server.networking.message.messages.StartGameMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe ServerCLIThread che ascolta dalla command line i comandi dell'utente
 */

public class ServerCLIThread extends Thread {
    private boolean running;

    public ServerCLIThread() {
        running = true;
    }

    @Override
    public void run() {

            Scanner scan = new Scanner(System.in);
            while (running) {
                System.out.println("scrivi un comando");
                String UserCommand = scan.next();

                switch (UserCommand) {
                    case "start" -> {
                        System.out.println("start cli");
                        Message messageStart = new StartGameMessage();
                        for (ServerThread serverThread : Server.getServerThreads()) {
                            try {
                                serverThread.sendMessage(messageStart);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    case "pause" -> {
                        Message messagePause = new PauseMessage();
                        for (ServerThread serverThread : Server.getServerThreads()) {
                            try {
                                serverThread.sendMessage(messagePause);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    case "quit" -> {
                        Message messageStop = new QuitMessage();
                        for (ServerThread serverThread : Server.getServerThreads()) {
                            try {
                                serverThread.sendMessage(messageStop);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    default -> System.err.println("COMANDO NON ESISTENTE");
                }
            }

    }

    public void quit(){
        this.running = false;
    }
}
