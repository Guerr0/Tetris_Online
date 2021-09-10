package client.gui.windows;

import client.logic.Game;
import client.logic.GameHandler;
import com.googlecode.lanterna.gui2.Window;
import client.gui.GUI;
import client.gui.windows.game.GameWindow;
import client.gui.windows.waiting.WaitingWindow;
import server.networking.message.GameStatus;

import java.util.ArrayList;

public class WindowSwitcher extends Thread {
    public enum WindowType{
        WELCOME, WAITING, GAME, TARGET, CONFIRM_CLOSE, CLOSING
    }

    private final GUI gui;
    private WelcomeWindow welcomeWindow;
    private WaitingWindow waitingWindow;
    private GameWindow gameWindow;
    private TargetPlayerWindow targetPlayerWindow;
    private ConfirmCloseWindow confirmCloseWindow;
    private ClosingWindow closingWindow;
    private CustomWindow previousWindow;
    private CustomWindow activeWindow;
    private boolean running;

    public WindowSwitcher(GUI gui){
        this.gui = gui;
        this.running = true;
        initializeWindows();
    }

    private void initializeWindows() {
        this.welcomeWindow = new WelcomeWindow(this);
        this.waitingWindow = new WaitingWindow(this);
        this.gameWindow = new GameWindow(this);
        this.targetPlayerWindow = new TargetPlayerWindow(this);
        this.confirmCloseWindow = new ConfirmCloseWindow(this);
        this.closingWindow = new ClosingWindow(this);
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameWindow.setGameHandler(gameHandler);
    }

    public void setGame(Game game){
        this.gameWindow.setGame(game);
    }

    public GUI getGui() {
        return gui;
    }

    public void restorePreviousWindow(){
        this.switchWindow(previousWindow.getWindowType());
    }

    public CustomWindow getActiveWindow(){
        return this.activeWindow;
    }

    public void switchWindow(WindowType windowType){
        this.previousWindow = (CustomWindow) gui.getTextGUI().getActiveWindow();

        switch (windowType) {
            case WELCOME -> {
                gui.setWindow(welcomeWindow);
                this.activeWindow = welcomeWindow;
                welcomeWindow.draw();
            }
            case WAITING -> {
                gui.setWindow(waitingWindow);
                this.activeWindow = waitingWindow;
                waitingWindow.draw();
            }
            case GAME -> {
                gui.setWindow(gameWindow);
                this.activeWindow = gameWindow;
                gameWindow.draw();
            }
            case TARGET -> {
                gui.setWindow(targetPlayerWindow);
                this.activeWindow = targetPlayerWindow;
                targetPlayerWindow.draw();
            }
            case CONFIRM_CLOSE -> {
                gui.setWindow(confirmCloseWindow);
                this.activeWindow = confirmCloseWindow;
                confirmCloseWindow.draw();
            }
            case CLOSING -> {
                gui.setWindow(closingWindow);
                this.activeWindow = closingWindow;
                closingWindow.draw();
            }
        }
    }
}
