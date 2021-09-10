package client.gui;

import com.googlecode.lanterna.gui2.TextGUI;

import java.io.IOException;

public class GUIThread extends Thread {
    private boolean running;
    private final TextGUI textGUI;
    private final int REFRESH_RATE;

    public GUIThread(TextGUI textGUI, int refreshRate){
        this.textGUI = textGUI;
        this.REFRESH_RATE = refreshRate;
        this.running = true;
    }

    @SuppressWarnings("BusyWait")
    public void run() {
        while(running){
            try {
                textGUI.updateScreen();
                Thread.sleep(REFRESH_RATE);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void quit(){
        this.running = false;
    }
}