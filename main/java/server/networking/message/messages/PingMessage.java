package server.networking.message.messages;

import server.networking.message.Message;

public class PingMessage extends Message {
    public PingMessage(){super(MessageType.PING);
    }
}
