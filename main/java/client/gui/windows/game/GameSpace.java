package client.gui.windows.game;

import client.logic.Cell;
import client.logic.Coordinate;
import client.logic.Game;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.ComponentRenderer;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.TextGUIGraphics;

import static com.googlecode.lanterna.TextColor.ANSI.*;

public class GameSpace extends EmptySpace {
    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    protected ComponentRenderer<EmptySpace> createDefaultRenderer(){
        return new ComponentRenderer<>(){

            @Override
            public TerminalSize getPreferredSize(EmptySpace emptySpace) {
                return new TerminalSize(24, 24);
            }

            @Override
            public void drawComponent(TextGUIGraphics textGUIGraphics, EmptySpace emptySpace) {
                textGUIGraphics.setBackgroundColor(BLACK);
                textGUIGraphics.fill(' ');
                if(game != null){
                    for (int i = 0; i < 12; i++) {
                        for (int j = 0; j < 24; j++) {
                            Cell currentCell = game.getGameMatrix()[i][j];
                            if(currentCell.getCellType() == Cell.CellType.FULL){
                                TextColor cellColor = getCellColor(currentCell.getCellColor());
                                textGUIGraphics.setForegroundColor(cellColor);
                                textGUIGraphics.putString(i*2, j, "██");
                            }
                        }
                    }
                    for (Coordinate coordinate : game.getFallingTetromino().getAbsoluteCoordinates()) {
                        TextColor cellColor = getCellColor(game.getFallingTetromino().getColor());
                        textGUIGraphics.setForegroundColor(cellColor);
                        textGUIGraphics.putString(coordinate.getX() * 2, coordinate.getY(), "██");
                    }
                }
            }
        };
    }

    protected TextColor getCellColor(Cell.CellColor cellColor){
        switch(cellColor){
            case RED -> {
                return RED;
            }
            case BLUE -> {
                return BLUE;
            }
            case CYAN -> {
                return CYAN;
            }
            case GREY -> {
                return WHITE_BRIGHT;
            }
            case BLACK -> {
                return BLACK;
            }
            case GREEN -> {
                return GREEN;
            }
            case ORANGE -> {
                return RED_BRIGHT;
            }
            case YELLOW -> {
                return YELLOW;
            }
            case FUCHSIA -> {
                return MAGENTA;
            }
        }
        return RED;
    }
}
