package client.gui.windows;

import com.googlecode.lanterna.input.KeyStroke;
import client.gui.windows.WindowSwitcher.WindowType;

import static client.gui.utils.CompareUtils.isPressed;
import static com.googlecode.lanterna.input.KeyType.*;
import static client.gui.windows.WindowSwitcher.WindowType.*;

public abstract class CustomHandledWindow extends CustomWindow {
    private final WindowSwitcher windowSwitcher;

    public CustomHandledWindow(WindowType windowType, WindowSwitcher windowSwitcher){
        super(windowType);
        this.windowSwitcher = windowSwitcher;
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if (isPressed(key, F10)) {
            this.windowSwitcher.switchWindow(CONFIRM_CLOSE);
            return true;
        }
        return false;
    }

}
