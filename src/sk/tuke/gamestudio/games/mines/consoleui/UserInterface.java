package sk.tuke.gamestudio.games.mines.consoleui;

import sk.tuke.gamestudio.games.mines.core.Field;

public interface UserInterface {
    boolean newGameStarted(Field field);
    void update();
}
