package client.gui.windows;

import com.googlecode.lanterna.gui2.BasicWindow;
import client.gui.windows.WindowSwitcher.WindowType;

public abstract class CustomWindow extends BasicWindow {
    private final WindowType windowType;

    public CustomWindow(WindowType windowType){
        this.windowType = windowType;
        draw();
    }

    public WindowType getWindowType() {
        return windowType;
    }

    public abstract void draw();
}
