package sk.tuke.gamestudio.games.stones.Settings;

import java.io.Serializable;

public class Settings implements Serializable {
    private final int rowCount;
    private final int columnCount;

    public static final Settings BEGINNER = new Settings(3,3);
    public static final Settings INTERMEDIATE = new Settings(4, 4);
    public static final Settings EXPERT = new Settings(5, 5);

    public Settings(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

}

