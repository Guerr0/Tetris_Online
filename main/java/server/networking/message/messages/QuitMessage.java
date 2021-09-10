package server.networking.message.messages;

import server.networking.message.Message;

public class QuitMessage extends Message {
    public QuitMessage() {
        super(MessageType.QUIT);
    }
}
