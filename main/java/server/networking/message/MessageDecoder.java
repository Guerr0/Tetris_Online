package server.networking.message;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * La classe MessageDecoder decodifica un messaggio e tramite uno switch-case attiva la conseguente
 * risposta
 */

public class MessageDecoder {
    public Message decodeMessage(ObjectInputStream incomingStream) throws IOException, ClassNotFoundException {
        return (Message) incomingStream.readObject();
    }
}
