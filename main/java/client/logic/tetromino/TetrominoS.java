package client.logic.tetromino;

import client.logic.Coordinate;
import client.logic.Cell.CellColor;

import static client.logic.tetromino.Tetromino.TetrominoType.BLOCK_S;

public class TetrominoS extends Tetromino {
    private final CellColor color;

    public TetrominoS(CellColor cellColor){
        super(BLOCK_S, createRelativeCoordinates());
        this.color = cellColor;
    }

    private static Coordinate[][] createRelativeCoordinates() {
        /*
         * 0        | 1         | 2         | 3
         * @ . . .  | . @ @ .   | @ . . .   | . @ @ .
         * @ @ . .  | @ @ . .   | @ @ . .   | @ @ . .
         * . @ . .  | . . . .   | . @ . .   | . . . .
         * . . . .  | . . . .   | . . . .   | . . . .
         */

        Coordinate[][] relativeCoordinates = createRelativeCoordinatesMatrix();

        relativeCoordinates[0] = new Coordinate[]{
                new Coordinate(0, 0),
                new Coordinate(0, 1),
                new Coordinate(1, 1),
                new Coordinate(1, 2)};
        relativeCoordinates[1] = new Coordinate[]{
                new Coordinate(0, 1),
                new Coordinate(1, 0),
                new Coordinate(1, 1),
                new Coordinate(2, 0)};
        relativeCoordinates[2] = new Coordinate[]{
                new Coordinate(0, 0),
                new Coordinate(0, 1),
                new Coordinate(1, 1),
                new Coordinate(1, 2)};
        relativeCoordinates[3] = new Coordinate[]{
                new Coordinate(0, 1),
                new Coordinate(1, 0),
                new Coordinate(1, 1),
                new Coordinate(2, 0)};

        return relativeCoordinates;
    }

    public CellColor getColor() {
        return color;
    }
}
