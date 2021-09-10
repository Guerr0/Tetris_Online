package server.networking.message.messages;

import server.networking.message.Message;

public class StartGameMessage extends Message {
    public StartGameMessage() {
        super(MessageType.START_GAME);
    }
}
