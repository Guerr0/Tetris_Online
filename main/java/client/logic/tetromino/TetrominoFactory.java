package client.logic.tetromino;

import client.logic.Cell.CellColor;
import client.logic.tetromino.Tetromino.TetrominoType;

import java.util.*;

import static client.logic.tetromino.Tetromino.TetrominoType.*;
import static client.logic.utils.MapUtils.createColorMap;

/**
 * Questa classe si occupa di creare le istanze dei tetramini. Possiede due metodi, entrambi restituiscono un tetramino: uno
 * crea uno specifico tetramino e l'altro crea un tetramino in maniera randomica.
 * */
public class TetrominoFactory {
    private Map<TetrominoType, CellColor> colorMap = createColorMap();

    public Tetromino createRandomTetromino(){
        TetrominoType randomTetrominoType = getRandomTetrominoType();
        return createTetromino(randomTetrominoType);
    }

    public Tetromino createTetromino(TetrominoType type){
        switch(type){
            case BLOCK_J:
                return new TetrominoJ(colorMap.get(BLOCK_J));
            case BLOCK_L:
                return new TetrominoL(colorMap.get(BLOCK_L));
            case BLOCK_O:
                return new TetrominoO(colorMap.get(BLOCK_O));
            case BLOCK_S:
                return new TetrominoS(colorMap.get(BLOCK_S));
            case BLOCK_T:
                return new TetrominoT(colorMap.get(BLOCK_T));
            case BLOCK_Z:
                return new TetrominoZ(colorMap.get(BLOCK_Z));
            default:
                // Nel caso di default crea I
                return new TetrominoI(colorMap.get(BLOCK_I));
       }
    }

    /**
     * Sceglie randomicamente un valore della {@link TetrominoType enum TetrominoType}
     * */
    private TetrominoType getRandomTetrominoType(){
        Random random = new Random();
        int numberOfTetrominos = TetrominoType.values().length;
        return TetrominoType.values()[random.nextInt(numberOfTetrominos)];
    }

    public void enableColorShuffle(){
        shuffleColorMap();
    }

    public void disableColorShuffle(){
        this.colorMap = createColorMap();
    }

    private void shuffleColorMap(){
        ArrayList<TetrominoType> keys = new ArrayList<>(colorMap.keySet());
        ArrayList<CellColor> values = new ArrayList<>(colorMap.values());
        Collections.shuffle(keys);
        for(int i = 0; i < keys.size(); i++){
            colorMap.put(keys.get(i), values.get(i));
        }
    }
}
