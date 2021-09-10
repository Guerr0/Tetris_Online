package client.logic;

import java.io.Serializable;

/**
 * Questa classe descrive una cella e il suo contenuto. Ogni cella puo' essere di una determinata tipologia, definita
 * dalla {@link CellType enum CellType} e avere un determinato colore, definito dalla {@link CellColor enum CellColor}.
 * */
public class Cell implements Serializable {
    private CellType cellType;
    private CellColor cellColor;

    public enum CellType {
        EMPTY, FULL, GHOST, JUNK
    }

    public enum CellColor {
        CYAN, BLUE, ORANGE, GREEN, FUCHSIA, RED, YELLOW, GREY, BLACK
    }

    public Cell() {
        this.cellColor = CellColor.BLACK;
        this.cellType = CellType.EMPTY;
    }

    /* GETTER */
    public CellType getCellType() {
        return cellType;
    }

    public CellColor getCellColor() {
        return cellColor;
    }

    /* SETTER */
    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public void setCellColor(CellColor cellColor) {
        this.cellColor = cellColor;
    }
}
