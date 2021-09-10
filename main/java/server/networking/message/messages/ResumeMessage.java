package server.networking.message.messages;

import server.networking.message.Message;

public class ResumeMessage extends Message {
    public ResumeMessage(){
        super(MessageType.RESUME);
    }
}
