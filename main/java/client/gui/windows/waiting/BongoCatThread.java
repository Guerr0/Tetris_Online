package client.gui.windows.waiting;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.gui2.ImageComponent;
import com.googlecode.lanterna.gui2.Panel;

import static client.gui.windows.waiting.BongoCatFrames.*;

class BongoCatThread extends Thread {
    private boolean running;
    private final Panel contentArea;

    public BongoCatThread(Panel contentArea){
        this.contentArea = contentArea;
        this.running = true;
    }

    @Override
    @SuppressWarnings("BusyWait")
    public void run() {
        ImageComponent imageComponent1 = makeImageComponent(FRAME_1);
        ImageComponent imageComponent2 = makeImageComponent(FRAME_2);

        while(running){
            contentArea.addComponent(imageComponent1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            contentArea.removeComponent(imageComponent1);
            contentArea.addComponent(imageComponent2);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            contentArea.removeComponent(imageComponent2);
        }
    }

    private ImageComponent makeImageComponent(String[] image) {
        TerminalSize imageSize = new TerminalSize(image[0].length(), image.length);
        TextImage textImage = new BasicTextImage(imageSize);
        for (int row = 0; row < image.length; row++) {
            fillImageLine(textImage, row, image[row]);
        }

        ImageComponent imageComponent = new ImageComponent();
        imageComponent.setTextImage(textImage);
        return imageComponent;
    }

    private void fillImageLine(TextImage textImage, int row, String line) {
        for (int x = 0; x < line.length(); x++) {
            char c = line.charAt(x);
            TextCharacter textCharacter = TextCharacter.fromCharacter(c)[0];
            textImage.setCharacterAt(x, row, textCharacter);
        }
    }

    public void quit(){
        this.running = false;
    }
}