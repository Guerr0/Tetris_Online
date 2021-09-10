package server.networking.message.messages;

import server.networking.message.Message;

public class EndGameMessage extends Message {
    public String sender;

    public EndGameMessage(String sender){
        super(MessageType.END_GAME);
        this.sender = sender;
    }
}
