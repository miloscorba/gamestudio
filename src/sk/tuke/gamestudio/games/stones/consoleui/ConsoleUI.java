package sk.tuke.gamestudio.games.stones.consoleui;

import sk.tuke.gamestudio.client.ScoreRestServiceClient;
import sk.tuke.gamestudio.games.TimeWatch;
import sk.tuke.gamestudio.games.stones.core.Direction;
import sk.tuke.gamestudio.games.stones.core.Field;
import sk.tuke.gamestudio.games.stones.core.GameState;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ConsoleUI implements UserInterface, Serializable {

    private Field field;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static final String FILENAME = "savedPlay.bin";
    File fileSaved = new File(FILENAME);
    public ScoreRestServiceClient scoreService = new ScoreRestServiceClient();
    TimeWatch watch = TimeWatch.start();

    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();

            if(field.getState() == GameState.SOLVED){
                update();
                System.out.println(ANSI_GREEN + "------------------------------------------------");
                System.out.println("Thats a boy/girl! Y O U   G O T   IT  !");
                System.out.println(ANSI_GREEN + "------------------------------------------------" + ANSI_RESET);

                return;
            } else if(field.getState() == GameState.EXIT){
                return;
            } else if (field.getState() == GameState.NEWGAME){
                System.out.println(ANSI_YELLOW + "------------------------");
                System.out.println("New game created." + ANSI_RESET);
                watch.reset();
                return;
            }
        } while(true);
    }

    @Override
    public void update(){
        prinHeaderLines();

        for (int row = 0; row < field.getRowCount(); row++){
            System.out.print(ANSI_GREEN + "\n|" + ANSI_RESET);
            for(int column = 0; column < field.getColumnCount(); column++){
                int valueOfStone = field.getStone(row, column).getValueOfStone();
                if(valueOfStone == 0){
                    System.out.printf(ANSI_GREEN + "     |" + ANSI_RESET);
                }
                else if (valueOfStone < 10) {
                    System.out.printf("  " + valueOfStone + ANSI_GREEN + "  |" + ANSI_RESET);
                } else {
                    System.out.printf(" " + valueOfStone + ANSI_GREEN + "  |" + ANSI_RESET);
                }
            }
            System.out.println();
            prinHeaderLines();
        }
        long minute = watch.getTime(TimeUnit.MINUTES);
        long second = watch.getTime(TimeUnit.SECONDS) - minute*60;
        System.out.println(ANSI_YELLOW + "\nGame is ON: " + minute + " min " + second + " sec "+ ANSI_RESET);

    }

    private void processInput() {
        System.out.println("\nMoves:" + ANSI_GREEN +  " w,s,a,d" + ANSI_RESET +
                "(up, down, left, right) \nOptions:" + ANSI_GREEN + " x,new" + ANSI_RESET + "(exit, new game)");
        System.out.println("Choose wisely: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        switch (input.toLowerCase()){
            case "w":
                field.move(Direction.UP);
                break;
            case "s":
                field.move(Direction.DOWN);
                break;
            case "a":
                field.move(Direction.LEFT);
                break;
            case "d":
                field.move(Direction.RIGHT);
                break;
            case "new":
                field.setState(GameState.NEWGAME);
                if(fileSaved.exists()) {
                    fileSaved.delete();
                }
                break;
            case "getTime":
                    scoreService.getBestScoresForGame("Stones");
                    break;
            case "x":
                field.state = GameState.EXIT;
                break;
        }
    }

    public void prinHeaderLines(){
        for(int i = 0; i < field.getColumnCount(); i++){
            System.out.printf(ANSI_GREEN + "+-----");
        }
        System.out.print("+" + ANSI_RESET);
    }
}
