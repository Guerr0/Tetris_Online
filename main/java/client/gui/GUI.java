package client.gui;

import client.logic.Game;
import client.logic.GameHandler;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import client.gui.windows.WindowSwitcher;
import server.networking.message.GameStatus;

import static client.gui.windows.WindowSwitcher.WindowType.*;

import java.io.IOException;
import java.util.ArrayList;

public class GUI {
    private MultiWindowTextGUI textGUI;
    private WindowSwitcher windowSwitcher;
    private GUIThread guiThread;
    private final int REFRESH_RATE;
    private Game game;
    private GameHandler gameHandler;
    private static ArrayList<GameStatus> gameStatusArrayList;

    public GUI(int fps) throws IOException {
        initializeGUI();
        initializeGUIThread();
        initializeWindowSwitcher();
        this.REFRESH_RATE = calculateRefreshRateFromFPS(fps);
    }

    public void initializeGUI() throws IOException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(116,42));
        Screen screen = defaultTerminalFactory.createScreen();
        this.textGUI = new MultiWindowTextGUI(screen);
        screen.startScreen();
    }

    public void initializeGUIThread(){
        this.guiThread = new GUIThread(this.textGUI, REFRESH_RATE);
        this.guiThread.start();
    }

    public void initializeWindowSwitcher(){
        this.windowSwitcher = new WindowSwitcher(this);
    }

    public void quitGUI() throws IOException {
        this.windowSwitcher.getGui().getTextGUI().getActiveWindow().close();
        this.guiThread.quit();
        this.textGUI.getScreen().stopScreen();
    }

    public void setGameStatusArrayList(ArrayList<GameStatus> gameStatusArrayList) {
        GUI.gameStatusArrayList = gameStatusArrayList;
    }

    public void setWindow(Window window){
        textGUI.addWindowAndWait(window);
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        this.windowSwitcher.setGameHandler(gameHandler);
    }

    public void setGame(Game game){
        this.game = game;
        this.windowSwitcher.setGame(game);
    }

    public MultiWindowTextGUI getTextGUI() {
        return textGUI;
    }

    public WindowSwitcher getWindowSwitcher(){
        return this.windowSwitcher;
    }

    private int calculateRefreshRateFromFPS(int fps){
        return 1000/fps;
    }

    public Game getGame() {
        return this.game;
    }

    public static ArrayList<GameStatus> getGameStatusArrayList(){
        return GUI.gameStatusArrayList;
    }
}