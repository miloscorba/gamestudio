package sk.tuke.gamestudio.games.stones.core;

import java.util.Random;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction getRandomDiretion() {
        Random random = new Random();
        Direction randomDirection = Direction.values()[random.nextInt(Direction.values().length)];
        return randomDirection;
    }
}
