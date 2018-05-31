package sk.tuke.gamestudio.games.mines;

import sk.tuke.gamestudio.games.mines.settings.Settings;
import sk.tuke.gamestudio.games.mines.consoleui.ConsoleUI;
import sk.tuke.gamestudio.games.mines.consoleui.UserInterface;
import sk.tuke.gamestudio.games.mines.core.Field;
import sk.tuke.gamestudio.games.AbstractGame;
import sk.tuke.gamestudio.games.TimeWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Main application class.
 */
public class Minesweeper extends AbstractGame {
    /** User interface. */
    private UserInterface userInterface;
    private TimeWatch watch;
    private Minesweeper instance;
    private Settings settings;
    private double score;
    /**
     * Constructor.
     */
    public Minesweeper(){

    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void run()  {
        instance = this;
        try {
            chooseSetting();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userInterface = new ConsoleUI();
        Field field = new Field(settings.getRowCount(), settings.getColumnCount(), settings.getMineCount());
        this.watch = TimeWatch.start();
        if(userInterface.newGameStarted(field))
        {
            setScore();
        } else {
            score = 0;
        }
    }

    public void setScore() {
        this.score = (settings.getColumnCount()*settings.getRowCount()+settings.getMineCount()*2)
                %watch.getTime(TimeUnit.SECONDS);
    }

    @Override
    public double getScore() {
        return this.score;
    }

    public void chooseSetting() throws IOException {
        System.out.println("************ M I N E S W E E P E R ************");
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

}
