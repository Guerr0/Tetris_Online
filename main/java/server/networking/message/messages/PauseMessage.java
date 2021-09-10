package server.networking.message.messages;

import server.networking.message.Message;

public class PauseMessage extends Message {
    public PauseMessage(){
        super(MessageType.PAUSE);
    }
}
