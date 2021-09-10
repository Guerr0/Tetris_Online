package client.gui.windows;

import com.googlecode.lanterna.gui2.*;

import java.util.Collections;

import static client.gui.windows.WindowSwitcher.WindowType.*;
import static com.googlecode.lanterna.gui2.Window.Hint.*;

public class TargetPlayerWindow extends CustomWindow {
    private final WindowSwitcher windowSwitcher;

    public TargetPlayerWindow(WindowSwitcher windowSwitcher) {
        super(TARGET);
        this.windowSwitcher = windowSwitcher;
        draw();
    }

    @Override
    public void draw() {
        Panel contentArea = new Panel();
        contentArea.addComponent(new Label("Designa un avversario delle righe spazzatura"));
        RadioBoxList<String> playerList = new RadioBoxList<>();
        playerList.addItem("player_0");
        playerList.addItem("player_1");
        playerList.addItem("player_2");
        Button selectPlayerButton = new Button("Seleziona", () -> {
            String designatedPlayer = playerList.getCheckedItem();
            System.out.println(designatedPlayer);
            windowSwitcher.switchWindow(GAME);
            this.close();
        });
        contentArea.addComponent(playerList);
        contentArea.addComponent(selectPlayerButton);
        this.setComponent(contentArea);
        this.setHints(Collections.singleton(CENTERED));
    }
}
