package client.gui.utils;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.util.Locale;

public class CompareUtils {
    public static boolean isPressed(KeyStroke keyStroke, String string){
        return keyStroke.equals(KeyStroke.fromString(string)) ||
                keyStroke.equals(KeyStroke.fromString(string.toUpperCase(Locale.ROOT)));
    }

    public static boolean isPressed(KeyStroke keyStroke, KeyType keyType){
        return keyStroke.getKeyType().equals(keyType);
    }
}
