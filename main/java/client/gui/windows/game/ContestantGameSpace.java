package client.gui.windows.game;

import client.logic.Cell;
import client.logic.Coordinate;
import client.logic.tetromino.Tetromino;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.ComponentRenderer;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.TextGUIGraphics;

import static com.googlecode.lanterna.TextColor.ANSI.*;

public class ContestantGameSpace extends GameSpace {
    private Cell[][] gameMatrix;
    private Tetromino fallingTetromino;

    public void setGame(Cell[][] gameMatrix, Tetromino fallingTetromino){
        this.gameMatrix = gameMatrix;
        this.fallingTetromino = fallingTetromino;
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
                if(gameMatrix != null && fallingTetromino != null){
                    for (int i = 0; i < 12; i++) {
                        for (int j = 0; j < 24; j++) {
                            Cell currentCell = gameMatrix[i][j];
                            if(currentCell.getCellType() == Cell.CellType.FULL){
                                TextColor cellColor = getCellColor(currentCell.getCellColor());
                                textGUIGraphics.setForegroundColor(cellColor);
                                textGUIGraphics.putString(i*2, j, "██");
                            }
                        }
                    }
                    for (Coordinate coordinate : fallingTetromino.getAbsoluteCoordinates()) {
                        TextColor cellColor = getCellColor(fallingTetromino.getColor());
                        textGUIGraphics.setForegroundColor(cellColor);
                        textGUIGraphics.putString(coordinate.getX() * 2, coordinate.getY(), "██");
                    }
                }
            }
        };
    }
}
