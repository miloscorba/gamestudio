package sk.tuke.gamestudio.games.stones.core;

import sk.tuke.gamestudio.games.stones.Settings.Settings;

import java.io.Serializable;
import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field implements Serializable {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    private int rowCount;
    private int columnCount;
    private Stone[][] stones;
    public GameState state = GameState.PLAYING;
    private StoneMoveable stoneMoveable;

    public Field(Settings settings) {
        this.rowCount = settings.getRowCount();
        this.columnCount = settings.getColumnCount();
        generate();
        getMoveAbleStone();
    }


    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public GameState getState() {
        return state;
    }

    public Stone getStone(int row, int column) {
        return stones[row][column];
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void generate() {
        stones = new Stone[this.rowCount][this.columnCount];
        int valueOfStone = 1;
        for(int row = 0; row < rowCount; row++){
            for(int column = 0; column < columnCount; column++){
                stones[row][column] = new Stone(valueOfStone);
                valueOfStone++;
            }
        }
        stones[rowCount-1][columnCount-1] = new StoneMoveable(rowCount-1, columnCount-1);
        getMoveAbleStone();
        for(int moveCount = 0; moveCount < 200; moveCount++){
            Direction direction;
            do{
                direction = Direction.getRandomDiretion();
            } while (!isPosibleMove(direction));

        }
    }

    public void move(Direction direction) {
        if(!isPosibleMove(direction)) {
            System.out.println(ANSI_RED + "************************");
            System.out.println("Out of field! Be carefull my friend." + ANSI_RESET);
        }

        if(isSolved()){
            state = GameState.SOLVED;
        }
    }

    private boolean isPosibleMove(Direction direction) throws  IndexOutOfBoundsException{
        int getRow = stoneMoveable.getRow();
        int getColumn = stoneMoveable.getColumn();
        int i=0, j =0;

        switch (direction){
            case UP:
                i = 1; break;
            case DOWN:
                i = -1; break;
            case LEFT:
                j = 1; break;
            case RIGHT:
                j = -1; break;
        }

        try {
            int value = stones[getRow + i][getColumn + j].getValueOfStone();
            stones[getRow][getColumn] = new Stone(value);
            stones[getRow + i][getColumn + j] = new StoneMoveable(getRow + i, getColumn + j);
            getMoveAbleStone();
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    private void getMoveAbleStone(){
        for(Stone[] s : stones){
            for(Stone s1 : s){
                if(s1 instanceof StoneMoveable){
                    stoneMoveable = new StoneMoveable(((StoneMoveable) s1).getRow(), ((StoneMoveable) s1).getColumn());
                }
            }
        }
    }

    public boolean isSolved(){
        int counter = 1;
        for(int i = 0; i < getRowCount(); i++){
            for(int j = 0; j < getColumnCount(); j++){
                if(stones[i][j].getValueOfStone() != counter){
                    return false;
                } else {
                    if (counter == rowCount*columnCount-1){
                        return true;
                    }
                }
                counter++;
            }
        }
        return true;
    }
}

