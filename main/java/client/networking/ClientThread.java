package client.networking;

import client.Client;
import client.gui.GUI;
import server.networking.message.Message;
import server.networking.message.MessageDecoder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Classe ClientThread lanciata da un client per connettersi al server.
 * classe molto dubbia
 */

// i while(running) non vanno assolutamente messi nei metodi,
// ma vanno messi nel main loop, che in questo caso e' run()

public class ClientThread extends Thread {
    private boolean running;
    private String playerName;
    private Socket socket;
    private ObjectOutputStream outgoingStream;
    private ObjectInputStream incomingStream;
    private MessageDecoder messageDecoder;
    private ClientMessageHandler clientMessageHandler;
    private Client client;
    private GUI gui;

    public ClientThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            init();
            while(running){
                readMessage();
            }
        } catch (Exception e) {
            System.out.println("exception in PlayerThread run()" + e);
        }
    }

    private void init() throws IOException {
        messageDecoder = new MessageDecoder();
        clientMessageHandler = new ClientMessageHandler(gui, this, client);
        connectToServer("127.0.0.1", 3111);
        running = true;
    }

    public void connectToServer(String host, int port) throws IOException {
        socket = new Socket(host, port);
        outgoingStream = new ObjectOutputStream(socket.getOutputStream());
        incomingStream = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(Message message) throws IOException {
        outgoingStream.reset();
        outgoingStream.writeObject(message);
    }

    public void readMessage() throws IOException, ClassNotFoundException, InterruptedException {
        if (socket.isConnected()) {
            Message receivedMessage = messageDecoder.decodeMessage(incomingStream);
            clientMessageHandler.handleMessage(receivedMessage);
        } else {
            throw new SocketException();
        }
    }

    public void endConnection() throws IOException {
        this.running = false;
        socket.close();
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
