package client.gui.windows.waiting;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;
import client.gui.windows.CustomHandledWindow;
import client.gui.windows.WindowSwitcher;
import java.util.Collections;

import static com.googlecode.lanterna.gui2.LinearLayout.Alignment.Fill;
import static client.gui.windows.WindowSwitcher.WindowType.*;
import static com.googlecode.lanterna.gui2.Direction.*;
import static com.googlecode.lanterna.gui2.Window.Hint.*;

public class WaitingWindow extends CustomHandledWindow {
    private BongoCatThread bongoCatThread;

    public WaitingWindow(WindowSwitcher windowSwitcher){
        super(WAITING, windowSwitcher);
        draw();
    }

    @Override
    public void draw(){
        Panel contentArea = new Panel();
        contentArea.setLayoutManager(new LinearLayout(HORIZONTAL));
        contentArea.addComponent(new Label("Sto aspettando che il server avvii il gioco..."));
        this.setComponent(contentArea);
        this.setHints(Collections.singleton(CENTERED));
    }

    public void easterEgg(){
        Panel contentArea = new Panel();
        Label title = new Label("Ecco il bongo cat per affrontare meglio l'attesa");
        contentArea.addComponent(title);
        contentArea.addComponent(new Separator(HORIZONTAL)
                .setLayoutData(LinearLayout.createLayoutData(Fill)));
        this.bongoCatThread = new BongoCatThread(contentArea);
        bongoCatThread.start();
        this.setComponent(contentArea);
        this.setHints(Collections.singleton(CENTERED));
    }

    @Override
    // Easter-egg
    public boolean handleInput(KeyStroke key) {
        if(key.equals(KeyStroke.fromString("."))){
            easterEgg();
            return true;
        }
        if(key.equals(KeyStroke.fromString(","))){
            if(bongoCatThread != null)
                bongoCatThread.quit();
            draw();
            return true;
        }
        if(bongoCatThread != null){
            bongoCatThread.quit();
        }
        return super.handleInput(key);
    }
}