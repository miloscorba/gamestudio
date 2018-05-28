package sk.tuke.gamestudio.games.mines;

import sk.tuke.gamestudio.Game;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.games.mines.Settings.Settings;
import sk.tuke.gamestudio.games.mines.consoleui.ConsoleUI;
import sk.tuke.gamestudio.games.mines.consoleui.UserInterface;
import sk.tuke.gamestudio.games.mines.core.Field;
import sk.tuke.gamestudio.games.stones.Settings.TimeWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Main application class.
 */
public class Minesweeper implements Game {
    /** User interface. */
    private UserInterface userInterface;
    private long startMillis;
    private Minesweeper instance;
    private Settings settings;
    /**
     * Constructor.
     */
    public Minesweeper() throws Exception{

    }

    public void choosesetting() throws IOException {
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

    public int getPlayingSeconds(){
        long time = System.currentTimeMillis() - startMillis;
        return  (int) time;
    }


    public Minesweeper getInstance() {
        return instance;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void run()  {
        TimeWatch watch = TimeWatch.start();
//        Score score = new Score(nameOfPlayer, clazz.getSimpleName (), (int) watch.time(TimeUnit.SECONDS));
//        srsc.addScore(score);
        instance = this;
        try {
            choosesetting();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userInterface = new ConsoleUI();
        Field field = new Field(settings.getRowCount(), settings.getColumnCount(), settings.getMineCount());
        startMillis = System.currentTimeMillis();
        userInterface.newGameStarted(field);
    }

    @Override
    public double getScore() {
        return 0;
    }

    /**
     * Main method.
     * @param args arguments
     */



}
