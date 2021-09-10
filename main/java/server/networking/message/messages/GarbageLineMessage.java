package server.networking.message.messages;

import server.networking.message.Message;

public class GarbageLineMessage extends Message {
    private String sender;
    private String target;

    public GarbageLineMessage(String sender, String target){
        super(MessageType.GARBAGE_LINE);
        this.sender = sender;
        this.target = target;
    }

    public String getSender() {
        return sender;
    }

    public String getTarget() {
        return target;
    }
}
