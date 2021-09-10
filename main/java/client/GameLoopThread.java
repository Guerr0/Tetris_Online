package client;

import client.logic.Game;
import client.logic.GameHandler;

import static client.logic.GameHandler.ActionType.DOWN;
import static client.logic.utils.DebugUtils.printGameStatus;

public class GameLoopThread extends Thread {
    private boolean running;
    private final boolean debug;
    private final Game game;
    private final GameHandler gameHandler;


    public GameLoopThread(boolean debug, Game game, GameHandler gameHandler){
        this.running = true;
        this.debug = debug;
        this.game = game;
        this.gameHandler = gameHandler;
    }

    public void quit(){
        this.running = false;
    }

    @Override
    public synchronized void start() {
        super.start();
        this.running = true;
    }

    @SuppressWarnings("BusyWaiting")
    @Override
    public void run() {
        while(running) {
            if (!debug) {
                try {
                    Thread.sleep(game.getMillisecondsDelay());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //printGameStatus(game.getGameMatrix(), game.getFallingTetromino());
                gameHandler.action(DOWN);
            }
        }
    }
}
