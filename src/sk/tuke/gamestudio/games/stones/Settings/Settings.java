package sk.tuke.gamestudio.games.stones.Settings;

import java.io.Serializable;

public class Settings implements Serializable {
    private final int rowCount;
    private final int columnCount;
    private final double coeficient;

    public static final Settings BEGINNER = new Settings(3,3, 300);
    public static final Settings INTERMEDIATE = new Settings(4, 4, 400);
    public static final Settings EXPERT = new Settings(5, 5, 500);

    public Settings(int rowCount, int columnCount, double coeficient) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.coeficient = coeficient;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public double getCoeficient() {
        return coeficient;
    }
}

