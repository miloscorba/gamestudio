package sk.tuke.gamestudio.games.stones;

import sk.tuke.gamestudio.games.AbstractGame;
import sk.tuke.gamestudio.games.stones.settings.Settings;
import sk.tuke.gamestudio.games.TimeWatch;
import sk.tuke.gamestudio.games.stones.consoleui.ConsoleUI;
import sk.tuke.gamestudio.games.stones.consoleui.UserInterface;
import sk.tuke.gamestudio.games.stones.core.Field;
import sk.tuke.gamestudio.games.stones.core.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Stones extends AbstractGame {
    private UserInterface userInterface;
    private Settings settings;
    private double score;
    private TimeWatch watch;

    public Stones() {
    }

    @Override
    public void run() {
        userInterface = new ConsoleUI();
        Field field;
        watch = TimeWatch.start();
        do {
            try {
                choosesetting();
            } catch (IOException e) {
                e.printStackTrace();
            }
            field = new Field(settings);
            watch.reset();
            userInterface.newGameStarted(field);
            if(field.getState() == GameState.EXIT){
                score=0;
                return;
            }
            if(field.getState() == GameState.SOLVED){
                score = setScore();
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

    public double setScore() {
        return (settings.getColumnCount()*settings.getRowCount()*settings.getCoeficient())
                %watch.getTime(TimeUnit.SECONDS);
    }

    @Override
    public double getScore() {
        return score;
    }
}

