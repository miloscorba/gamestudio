package sk.tuke.gamestudio.games.stones.consoleui;

import sk.tuke.gamestudio.games.stones.core.Field;
import sk.tuke.gamestudio.service.ScoreException;

public interface UserInterface {
    void newGameStarted(Field field);

    void update();

    Field load() throws Exception;

    void save() throws Exception;
}
