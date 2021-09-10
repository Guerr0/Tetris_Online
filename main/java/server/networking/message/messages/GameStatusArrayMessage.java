package server.networking.message.messages;

import server.networking.message.GameStatus;
import server.networking.message.Message;

import java.util.ArrayList;

public class GameStatusArrayMessage extends Message {
    private final ArrayList<GameStatus> gameStatusArrayList;

    public GameStatusArrayMessage(ArrayList<GameStatus> gameStatusArrayList){
        super(MessageType.GAME_STATUS_ARRAY);
        this.gameStatusArrayList = gameStatusArrayList;
    }

    public ArrayList<GameStatus> getGameStatusArrayList() {
        return gameStatusArrayList;
    }
}
