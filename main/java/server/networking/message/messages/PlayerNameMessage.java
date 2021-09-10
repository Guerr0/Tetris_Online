package server.networking.message.messages;

import server.networking.message.Message;

public class PlayerNameMessage extends Message {
    private String playerNameProposal;
    private boolean isNameAvailable;

    public PlayerNameMessage(String playerNameProposal){
        super(MessageType.PLAYER_NAME);
        this.playerNameProposal = playerNameProposal;
    }

    public PlayerNameMessage(boolean isNameAvailable, String playerNameProposal){
        super(MessageType.PLAYER_NAME);
        this.isNameAvailable = isNameAvailable;
        this.playerNameProposal = playerNameProposal;
    }

    public String getPlayerNameProposal() {
        return playerNameProposal;
    }

    public boolean isNameAvailable() {
        return isNameAvailable;
    }
}
