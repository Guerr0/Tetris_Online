package client.gui.windows.game;

import client.Client;
import client.networking.ClientThread;
import client.logic.GameHandler;
import com.googlecode.lanterna.input.KeyStroke;
import client.gui.windows.WindowSwitcher;

import server.networking.message.Message;
import server.networking.message.messages.GarbageLineMessage;

import java.io.IOException;

import static client.gui.utils.CompareUtils.isPressed;
import static client.gui.windows.WindowSwitcher.WindowType.*;
import static com.googlecode.lanterna.input.KeyType.*;

public class GameWindowInputHandler {
    private final WindowSwitcher windowSwitcher;
    private GameHandler gameHandler;

    public GameWindowInputHandler(WindowSwitcher windowSwitcher){
        this.windowSwitcher = windowSwitcher;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public boolean handle(KeyStroke keyStroke)  {
        if (isPressed(keyStroke, "t")) {
            windowSwitcher.switchWindow(TARGET);
            return true;
        }
        if (isPressed(keyStroke, ArrowRight)) {
            //System.out.println("MOVE RIGHT");
            this.gameHandler.action(GameHandler.ActionType.RIGHT);
            return true;
        }
        if (isPressed(keyStroke, ArrowLeft)) {
            //System.out.println("MOVE LEFT");
            this.gameHandler.action(GameHandler.ActionType.LEFT);
            return true;
        }
        if (isPressed(keyStroke, ArrowDown)) {
            //System.out.println("MOVE DOWN");
            this.gameHandler.action(GameHandler.ActionType.DOWN);
            return true;
        }
        if (isPressed(keyStroke, ".")) {
            //System.out.println("ROTATE CW");
            this.gameHandler.action(GameHandler.ActionType.ROTATE_CLOCKWISE);
            return true;
        }
        if (isPressed(keyStroke, ",")) {
            //System.out.println("ROTATE CCW");
            this.gameHandler.action(GameHandler.ActionType.ROTATE_COUNTER_CLOCKWISE);
            return true;
        }
        if (isPressed(keyStroke, " ")) {
            //System.out.println("DROP");
            this.gameHandler.action(GameHandler.ActionType.DROP);
            return true;
        }
        if (isPressed(keyStroke, "x")) {
            ClientThread clientThread = gameHandler.getClient().getClientThread();
            Message msg = new GarbageLineMessage(clientThread.getPlayerName(), "designatedPlayer");
            try {
                clientThread.sendMessage(msg);
                System.out.println("HO INVIATO MESSAGGIO SJL");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (isPressed(keyStroke, "z")) {
            //System.out.println("HOLD BLOCK");
            this.gameHandler.action(GameHandler.ActionType.HOLD_BLOCK);
            return true;
        }
        return false;
    }
}
