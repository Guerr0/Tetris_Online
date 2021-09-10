package client.gui.windows;

import com.googlecode.lanterna.gui2.*;

import java.io.IOException;
import java.util.Collections;

import static com.googlecode.lanterna.gui2.Direction.HORIZONTAL;
import static com.googlecode.lanterna.gui2.Direction.VERTICAL;
import static com.googlecode.lanterna.gui2.LinearLayout.Alignment.Fill;
import static com.googlecode.lanterna.gui2.Window.Hint.CENTERED;
import static client.gui.windows.WindowSwitcher.WindowType.*;

public class ConfirmCloseWindow extends CustomWindow {
    private final WindowSwitcher windowSwitcher;

    public ConfirmCloseWindow(WindowSwitcher windowSwitcher) {
        super(CONFIRM_CLOSE);
        this.windowSwitcher = windowSwitcher;
    }

    @Override
    public void draw() {
        Panel contentArea = new Panel();
        contentArea.setLayoutManager(new LinearLayout(VERTICAL));
        contentArea.addComponent(new Label("Sei sicuro di volere uscire?"));
        contentArea.addComponent(new Separator(HORIZONTAL).setLayoutData(LinearLayout.createLayoutData(Fill)));
        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(HORIZONTAL));
        buttonPanel.addComponent(new Button("SI", () -> {
            try {
                this.windowSwitcher.getGui().quitGUI();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        buttonPanel.addComponent(new Button("NO", () -> {
            // La method reference causa una null exception
            this.windowSwitcher.restorePreviousWindow();
        }));
        contentArea.addComponent(buttonPanel);
        this.setComponent(contentArea);
        this.setHints(Collections.singleton(CENTERED));
    }
}