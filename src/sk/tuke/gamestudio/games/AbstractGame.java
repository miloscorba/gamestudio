package sk.tuke.gamestudio.games;

public abstract class AbstractGame implements Game {
    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
