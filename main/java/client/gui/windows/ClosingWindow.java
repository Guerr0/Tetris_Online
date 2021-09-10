package client.gui.windows;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.Collections;

import static com.googlecode.lanterna.gui2.Window.Hint.*;
import static client.gui.windows.WindowSwitcher.WindowType.*;

public class ClosingWindow extends CustomHandledWindow {
    public ClosingWindow(WindowSwitcher windowSwitcher) {
        super(CLOSING, windowSwitcher);
        draw();
    }

    @Override
    public void draw() {
        Panel contentArea = new Panel();
        contentArea.addComponent(new Label("Hai vinto!"));
        contentArea.addComponent(new Label("Hai perso!"));
        this.setComponent(contentArea);
        this.setHints(Collections.singleton(CENTERED));
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        return super.handleInput(key);
    }
}
