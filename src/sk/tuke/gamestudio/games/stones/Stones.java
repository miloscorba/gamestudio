package sk.tuke.gamestudio.games.stones;

import sk.tuke.gamestudio.Game;
import sk.tuke.gamestudio.games.stones.Settings.Settings;
import sk.tuke.gamestudio.games.stones.Settings.TimeWatch;
import sk.tuke.gamestudio.games.stones.consoleui.ConsoleUI;
import sk.tuke.gamestudio.games.stones.consoleui.UserInterface;
import sk.tuke.gamestudio.games.stones.core.Field;
import sk.tuke.gamestudio.games.stones.core.GameState;
import sk.tuke.gamestudio.service.ScoreException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Stones implements Game {
    private UserInterface userInterface;
    private int Score;
    public Stones() {
    }

    public Settings settings;
    public double score;

    @Override
    public void run() {
        userInterface = new ConsoleUI();
        Field field;
        TimeWatch watch = TimeWatch.start();

        do {
            try {
                choosesetting();
            } catch (IOException e) {
                e.printStackTrace();
            }
            watch.reset();
            field = new Field(settings);
            userInterface.newGameStarted(field);
            if(field.getState() == GameState.EXIT){
                return;
            }
            if(field.getState() == GameState.SOLVED){
                score = field.getScore();
            }
        } while (field.getState() == GameState.NEWGAME);
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Settings getSettings() {
        return settings;
    }

    public void choosesetting() throws IOException {
        System.out.println("* * * * * * * * STONES * * * * * * * *");
        while(true){
            System.out.println("Options: 1 - Begginer, 2 - Intermediate, 3 - Expert");
            System.out.println("Choose: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();
            switch(input) {
                case "1":   setSettings(Settings.BEGINNER);
                    return;
                case "2":   setSettings(Settings.INTERMEDIATE);
                    return;
                case "3":   setSettings(Settings.EXPERT);
                    return;
                default:
                    System.out.println("Try again -_- ");
            }
        }
    }

    @Override
    public double getScore() {
        return score;
    }
}

