package client.logic;

import client.logic.tetromino.Tetromino;
import client.logic.tetromino.TetrominoFactory;

import static client.logic.Cell.CellType.*;

/**
 * Questa classe rappresenta un'azione di gioco. Un'istanza di essa viene creata ad ogni chiamata dei metodi
 * {@link GameHandler#action(GameHandler.ActionType) action(actionType)} e
 * {@link GameHandler#action(GameHandler.ActionType, int junkLines) action(actionType, junkLines)} in {@link GameHandler}.
 * <br><br>
 * Ogni azione (pubblica, esempio {@link #moveRight() moveRight()}) utilizza dei metodi di supporto
 * privati per effettuare i dovuti controlli (ad esempio capire quando un tetramino collide). Al pubblico vengono
 * quindi esposti solo i comandi necessari, la logica e' protetta e intoccabile se non attraverso {@link GameHandler}.
 * */

public class GameAction {
    private final Game game;
    private final Tetromino fallingTetromino;
    private final Cell[][] gameMatrix;

    public GameAction(Game game){
        this.game = game;
        this.fallingTetromino = game.getFallingTetromino();
        this.gameMatrix = game.getGameMatrix();
    }

    public void moveRight(){
        move(1,0);
    }

    public void moveLeft(){
        move(-1,0);
    }

    public void moveDown(){
        move(0,1);
    }

    /**
     * Il metodo drop() continua a spostare in basso il tetramino, tramite la funzione
     * {@link #moveDown() moveDown()}, finche' non collide (questo controllo e' fatto da {@link #fits() fits()}) coi
     * limiti del campo da gioco o un altro tetramino.
     * */
    public void drop(){
        while(fits()){
            moveDown();
        }
    }

    public void rotateClockwise(){
        this.fallingTetromino.rotateClockwise();
        if(!fits()){
            this.rotateCounterClockwise();
        }
    }

    public void rotateCounterClockwise(){
        this.fallingTetromino.rotateCounterClockwise();
        if(!fits()){
            this.rotateClockwise();
        }
    }

    /**
     * Metodo per aggiungere le junk lines al campo da gioco.
     * <br>
     * Se la condizione {@link #matrixFits(int) matrixFits(junkLines)} e' rispettata le celle piene vengono spostate
     * in alto di n posizioni, dove n = junkLines. Successivamente vengono riempite le n linee piu' basse del campo da
     * gioco con celle JUNK. Se il tetramino dovesse collidere nel processo, verra' spostato in alto.
     * */
    public void addJunkLines(int junkLines){
        if(matrixFits(junkLines)){
            for (int i = 0; i < game.getCols(); i++) {
                for (int j = 0; j < game.getRows(); j++) {
                    if(gameMatrix[i][j].getCellType() == FULL){
                        gameMatrix[i][j-junkLines].setCellType(FULL);
                        gameMatrix[i][j].setCellType(EMPTY);
                    }
                }
                for (int k = 1; k <= junkLines; k++) {
                    gameMatrix[i][game.getRows()-k].setCellType(JUNK);
                }
            }
            for (int i = 0; i < junkLines; i++) {
                if(!fits()){
                    move(0,-1);
                }
            }
        } else {
            quitGame();
        }
    }

    /**
     * Analogamente a {@link Game#quitGame() quitGame()} in {@link Game}, questo metodo e' solo un tramite, che chiamera'
     * {@link Game#quitGame() quitGame()}, che a sua volta chiamera' {@link client.Client#quitGame() quitGame()}, il metodo di
     * {@link client.Client Client} che si occupa di terminare il programma in maniera corretta.
     * */
    public void quitGame(){
        game.quitGame();
    }

    /**
     * Scambia il tetramino in caduta con il tetramino nella sezione di <i>hold</i>.
     * */
    public void holdBlock(){
        Tetromino fallingTetromino = game.getFallingTetromino();
        Tetromino holdTetromino = game.getHoldTetromino();

        // Null check su holdTetromino
        // TODO Coordinate di origine da modificare
        if(holdTetromino == null){
            game.setHoldTetromino(fallingTetromino);
            game.setFallingTetromino(new TetrominoFactory().createRandomTetromino());
        } else {
            game.setFallingTetromino(holdTetromino);
            game.setHoldTetromino(fallingTetromino);
        }
    }

    /**
     * Metodo di supporto che si occupa di muovere il tetramino nella maniera indicata.
     * <br><br>
     * Il metodo aggiunge un offset (verticale o orizzontale) alle coordinate d'origine del tetramino.
     * <br>
     * Successivamente, viene effettuato un controllo con il metodo {@link #fits() fits()}. Se la condizione non e'
     * soddisfatta (e quindi il tetramino collide), vengono impostate le coordinate d'origine precedenti. Questo viene
     * fatto tramite i metodi {@link #resetHorizontalOffset(int) resetHorizontalOffset()} e
     * {@link #resetVerticalOffset(int) resetVerticalOffset()}.
     * <br>
     * Nel caso sia presente un movimento di tipo verticale viene fatto un ulteriore controllo: la matrice del campo
     * di gioco deve essere nei limiti e non devono esserci celle FULL in alto (nella riga 0)
     * */
    private void move(int horizontalOffset, int verticalOffset){
        // Aggiungiamo l'offset all'origine
        Coordinate originCoordinates = fallingTetromino.getOriginCoordinates();

        fallingTetromino.setOriginCoordinates(
                originCoordinates.getX()+horizontalOffset,
                originCoordinates.getY()+verticalOffset
        );

        // Se collide entra nell'if
        if(!fits()){
            if(horizontalOffset != 0){
                // Riporta alla condizione iniziale l'origine
                resetHorizontalOffset(horizontalOffset);
            }
            if(verticalOffset != 0){
                // Riporta alla condizione iniziale l'origine
                resetVerticalOffset(verticalOffset);
                // Cementa l'attuale blocco fallingTetromino al campo di gioco
                game.settleBlock();
                /* Controlla che il campo di gioco rispetti le condizioni. Il valore assoluto serve perche' altrimenti
                 * il movimento con un offset verticale negativo (movimento verso l'alto) invaliderebbe il controllo della
                 * matrice di gioco.
                 */
                if(!matrixFits(Math.abs(verticalOffset))){
                    quitGame();
                }
            }
        }
    }

    private void resetHorizontalOffset(int horizontalOffset){
        Coordinate originCoordinates = fallingTetromino.getOriginCoordinates();

        fallingTetromino.setOriginCoordinates(
                originCoordinates.getX()-horizontalOffset,
                originCoordinates.getY());
    }

    private void resetVerticalOffset(int verticalOffset){
        Coordinate originCoordinates = fallingTetromino.getOriginCoordinates();

        fallingTetromino.setOriginCoordinates(
                originCoordinates.getX(),
                originCoordinates.getY()-verticalOffset);
    }

    /**
     * Questo metodo definisce le condizioni che un blocco deve rispettare per rimanere fallingTetromino.
     * <br>
     * Viene controllata ogni coppia cartesiana di absoluteCoordinates e viene controllato che le coordinate non
     * eccedano i limiti previsti dalla dimensione di Game o che il blocco falling si sovrapponga a delle celle FULL
     * del campo da gioco.
     * */
    private boolean fits(){
        Coordinate[] absoluteCoordinates = fallingTetromino.getAbsoluteCoordinates();

        for (Coordinate coordinate : absoluteCoordinates) {
            int x = coordinate.getX();
            int y = coordinate.getY();
            if(x < 0 || x >= game.getCols()) return false;
            if(y < 0 || y >= game.getRows()) return false;
            if(gameMatrix[x][y].getCellType() == FULL) return false;
        }
        return true;
    }

    /**
     * Questo metodo controlla che la matrice del campo da gioco possa essere spostata in alto senza che una cella FULL
     * collida con i limiti del campo di gioco.
     * */
    private boolean matrixFits(int verticalOffset){
        for(int i = 0; i < game.getCols(); i++){
            for(int j = 0; j < game.getRows(); j++){
                if(gameMatrix[i][j].getCellType() == FULL){
                    if(j-verticalOffset <= 0) return false;
                }
            }
        }
        return true;
    }
}
