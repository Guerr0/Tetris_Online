package client.logic;

import client.Client;

import static client.logic.GameHandler.ActionType.*;

/**
 * Questa classe si occupa di ricevere comandi (identificati dalla enum ActionType).
 * Una volta identificato il comando viene creata una nuova classe {@link GameAction GameAction} e viene chiamato
 * il metodo interessato, che si occupa di effettuare un'operazione su Game (ad esempio muovere a destra il tetramino).
 * */
public class GameHandler {
    private final Game game;

    Client client;

    public enum ActionType {
        RIGHT, LEFT, DOWN, DROP, ROTATE_CLOCKWISE, ROTATE_COUNTER_CLOCKWISE, ADD_JUNK_LINES, QUIT_GAME, HOLD_BLOCK
    }

    public GameHandler(Game game, Client client){
        this.game = game;
        this.client = client;
    }

    public void action(ActionType actionType){
        GameAction gameAction = new GameAction(game);
        switch (actionType) {
            case RIGHT -> gameAction.moveRight();
            case LEFT -> gameAction.moveLeft();
            case DOWN -> gameAction.moveDown();
            case DROP -> gameAction.drop();
            case ROTATE_CLOCKWISE -> gameAction.rotateClockwise();
            case ROTATE_COUNTER_CLOCKWISE -> gameAction.rotateCounterClockwise();
            case HOLD_BLOCK -> gameAction.holdBlock();
            case QUIT_GAME -> gameAction.quitGame();
        }
    }
    public void action(ActionType actionType, int junkLines){
        GameAction gameAction = new GameAction(game);
        if(actionType == ADD_JUNK_LINES){
            gameAction.addJunkLines(junkLines);
        }
    }

    public Game getGame() {
        return game;
    }

    public Client getClient() {
        return client;
    }

}
