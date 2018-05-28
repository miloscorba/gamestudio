package sk.tuke.gamestudio.games.stones.core;

import java.io.Serializable;

public class Stone implements Serializable {
    private int valueOfStone;

    public Stone() {
    }

    public Stone(int valueOfStone) {
        this.valueOfStone = valueOfStone;
    }

    public int getValueOfStone() {
        return valueOfStone;
    }
}
