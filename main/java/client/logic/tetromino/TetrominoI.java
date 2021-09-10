package client.logic.tetromino;

import client.logic.Coordinate;
import client.logic.Cell.CellColor;

import static client.logic.tetromino.Tetromino.TetrominoType.BLOCK_I;

public class TetrominoI extends Tetromino {
    private final CellColor color;

    public TetrominoI(CellColor cellColor){
        super(BLOCK_I, createRelativeCoordinates());
        this.color = cellColor;
    }

    private static Coordinate[][] createRelativeCoordinates() {
        /*
         * 0        | 1         | 2         | 3
         * . @ . .  | . . . .   | . . @ .   | . . . .
         * . @ . .  | @ @ @ @   | . . @ .   | . . . .
         * . @ . .  | . . . .   | . . @ .   | @ @ @ @
         * . @ . .  | . . . .   | . . @ .   | . . . .
         */

        Coordinate[][] relativeCoordinates = createRelativeCoordinatesMatrix();

        relativeCoordinates[0] = new Coordinate[]{
                new Coordinate(1, 0),
                new Coordinate(1, 1),
                new Coordinate(1, 2),
                new Coordinate(1, 3)};
        relativeCoordinates[1] = new Coordinate[]{
                new Coordinate(0, 1),
                new Coordinate(1, 1),
                new Coordinate(2, 1),
                new Coordinate(3, 1)};
        relativeCoordinates[2] = new Coordinate[]{
                new Coordinate(2, 0),
                new Coordinate(2, 1),
                new Coordinate(2, 2),
                new Coordinate(2, 3)};
        relativeCoordinates[3] = new Coordinate[]{
                new Coordinate(0, 2),
                new Coordinate(1, 2),
                new Coordinate(2, 2),
                new Coordinate(3, 2)};

        return relativeCoordinates;
    }

    public CellColor getColor() {
        return color;
    }
}
