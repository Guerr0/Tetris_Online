package client.gui.windows;

import client.Client;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import server.networking.message.messages.PlayerNameMessage;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import static com.googlecode.lanterna.gui2.Window.Hint.*;
import static client.gui.windows.WindowSwitcher.WindowType.*;
import static com.googlecode.lanterna.TextColor.ANSI.*;
import static com.googlecode.lanterna.gui2.Direction.*;
import static com.googlecode.lanterna.gui2.LinearLayout.Alignment.Fill;

public class WelcomeWindow extends CustomWindow {
    private Panel usernamePanel;
    private TextBox usernameTextBox;
    private final Label usernameNotAvailableLabel;
    private final WindowSwitcher windowSwitcher;
    public static final ReentrantLock usernameLock = new ReentrantLock();
    public static boolean usernameAvailable = false;

    public WelcomeWindow(WindowSwitcher windowSwitcher){
        super(WELCOME);
        this.windowSwitcher = windowSwitcher;
        this.usernameNotAvailableLabel = new Label("L'username non e' disponibile\nSelezionane un altro")
                .setForegroundColor(RED);
        draw();
    }

    @Override
    public void draw(){
        Panel contentArea = new Panel();
        contentArea.addComponent(new Label("Benvenuto in Tetrash!"));
        this.usernamePanel = new Panel();
        usernamePanel.addComponent(new Label("Inserisci un nome per giocare:"));
        usernamePanel.addComponent(new Separator(HORIZONTAL)
                .setLayoutData(LinearLayout.createLayoutData(Fill)));
        Pattern pattern = Pattern.compile("[A-Za-z0-9_-]{0,25}+");
        this.usernameTextBox = new TextBox(new TerminalSize(26,1))
                .setValidationPattern(pattern);
        usernamePanel.addComponent(usernameTextBox);
        usernamePanel.addComponent(new Button("Invio", () -> {
            try {
                submitButton(usernameTextBox.getText());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }));
        contentArea.addComponent(usernamePanel);
        this.setComponent(contentArea);
        this.setHints(Collections.singleton(CENTERED));
    }

    private void submitButton(String username) throws IOException, InterruptedException {
        if(isNameAvailable(username)){
            windowSwitcher.switchWindow(WAITING);
        } else {
            if(!this.usernamePanel.getChildren().contains(usernameNotAvailableLabel)){
                this.usernamePanel.addComponent(usernameNotAvailableLabel);
            }
            this.usernameTextBox.setText("");
        }
    }

    private boolean isNameAvailable(String username) throws IOException, InterruptedException {
        PlayerNameMessage playerNameMessage = new PlayerNameMessage(username);
        Client.getClientThread().sendMessage(playerNameMessage);
        synchronized (usernameLock){
            usernameLock.wait();
        }
        return usernameAvailable;
    }
}