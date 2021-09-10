package client.gui.windows.game;

import client.gui.GUI;
import client.logic.Game;
import client.logic.GameHandler;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import client.gui.windows.CustomHandledWindow;
import client.gui.windows.WindowSwitcher;
import server.networking.message.GameStatus;

import java.util.ArrayList;
import java.util.Collections;

import static client.gui.windows.WindowSwitcher.WindowType.*;
import static com.googlecode.lanterna.gui2.Direction.*;
import static com.googlecode.lanterna.gui2.LinearLayout.Alignment.Fill;
import static com.googlecode.lanterna.gui2.Window.Hint.*;
import static com.googlecode.lanterna.TextColor.ANSI.*;

public class GameWindow extends CustomHandledWindow {
    private final GameWindowInputHandler gameWindowInputHandler;
    private Game game;
    private GameHandler gameHandler;
    private String targetPlayer;
    private GameSpace gameSpace;
    private ContestantGameSpace contestantGameSpace0;
    private ContestantGameSpace contestantGameSpace1;
    private ContestantGameSpace contestantGameSpace2;

    public GameWindow(WindowSwitcher windowSwitcher) {
        super(GAME, windowSwitcher);
        this.gameWindowInputHandler = new GameWindowInputHandler(windowSwitcher);
        draw();
    }

    public void setGame(Game game) {
        this.game = game;
        this.gameSpace.setGame(game);
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        this.gameWindowInputHandler.setGameHandler(gameHandler);
    }

    public void setContestantGameSpaceGameStatus(){
        ArrayList<GameStatus> gameStatusArrayList = GUI.getGameStatusArrayList();
        if(gameStatusArrayList != null){
            try{
                this.contestantGameSpace0.setGame(gameStatusArrayList.get(0).getGameMatrix(),
                        gameStatusArrayList.get(0).getFallingTetromino());
            } catch(IndexOutOfBoundsException ignored){}
            try{
                this.contestantGameSpace1.setGame(gameStatusArrayList.get(1).getGameMatrix(),
                        gameStatusArrayList.get(1).getFallingTetromino());
            } catch(IndexOutOfBoundsException ignored){}
            try{
                this.contestantGameSpace2.setGame(gameStatusArrayList.get(2).getGameMatrix(),
                        gameStatusArrayList.get(2).getFallingTetromino());
            } catch(IndexOutOfBoundsException ignored){}
        }
    }

    @Override
    public void draw() {
        Panel contentArea = new Panel().setLayoutManager(new LinearLayout(HORIZONTAL));
        Panel gameArea = drawPlayerArea();
        Panel rightPanel = new Panel();
        rightPanel.setLayoutManager(new LinearLayout(VERTICAL));
        Panel contestantsArea = new Panel().setLayoutManager(new LinearLayout(HORIZONTAL));
        initializeGameSpaces();
        contestantsArea.addComponent(contestantGameSpace0);
        contestantsArea.addComponent(contestantGameSpace1);
        contestantsArea.addComponent(contestantGameSpace2);
        rightPanel.addComponent(contestantsArea.withBorder(Borders.singleLine()));
        rightPanel.addComponent(drawServerStats());
        contentArea.addComponent(gameArea);
        contentArea.addComponent(rightPanel);
        this.setComponent(contentArea);
        this.setHints(Collections.singleton(CENTERED));
    }

    private void initializeGameSpaces() {
        this.contestantGameSpace0 = new ContestantGameSpace();
        this.contestantGameSpace1 = new ContestantGameSpace();
        this.contestantGameSpace2 = new ContestantGameSpace();
        this.setContestantGameSpaceGameStatus();
    }

    private Panel drawServerStats(){
        Panel serverStats = new Panel();
        serverStats.setLayoutManager(new LinearLayout(VERTICAL));
        serverStats.addComponent(new Label("Server IP:\t"));
        serverStats.addComponent(new Label("Server port:\t"));
        serverStats.addComponent(new Label("Ping:\t"));
        return serverStats;
    }

    private Panel drawPlayerArea(){
        Panel gameArea = new Panel().setLayoutManager(new LinearLayout(VERTICAL));
        drawPlayerGame(gameArea);
        Panel statsPanel = new Panel();
        statsPanel.addComponent(new Label("Player Name:\t"));
        statsPanel.addComponent(new Label("Score:\t"));
        statsPanel.addComponent(new Separator(HORIZONTAL)
                .setLayoutData(LinearLayout.createLayoutData(Fill)));
        drawTargetPlayer(statsPanel);
        gameArea.addComponent(statsPanel);
        return gameArea;
    }

    private void drawPlayerGame(Panel gameArea){
        this.gameSpace = new GameSpace();
        gameArea.addComponent(gameSpace.withBorder(Borders.doubleLine()));
    }

    private void drawTargetPlayer(Panel statsPanel){
        if(targetPlayer == null) {
            Panel targetPlayerPanel = new Panel();
            targetPlayerPanel.setLayoutManager(new LinearLayout(HORIZONTAL));
            targetPlayerPanel.addComponent(new Label("Target Player:\t"));
            targetPlayerPanel.addComponent(new Label("none").setForegroundColor(RED));
            statsPanel.addComponent(targetPlayerPanel);
        } else {
            statsPanel.addComponent(new Label("Target Player:\t" + targetPlayer));
        }
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if(gameWindowInputHandler.handle(key)){
            return true;
        }
        return super.handleInput(key);
    }
}
