package sk.tuke.gamestudio.games.mines.consoleui;

import sk.tuke.gamestudio.games.mines.Settings.TimeWatch;
import sk.tuke.gamestudio.games.mines.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /** Playing field. */
    private Field field;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";

    TimeWatch watch = TimeWatch.start();

    /** Input reader. */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Starts the game.
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        System.out.println("************ M I N E S W E E P E R ************");
        do {
            update();
            try {
                processInput();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(field.getState() == GameState.SOLVED){
                update();
                System.out.println(ANSI_GREEN + "------------------------------------------------");
                System.out.println("Wohoooo! YOU WIN !!");
                System.out.println("------------------------------------------------" + ANSI_RESET);
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                //bestTimes.addPlayerTime(input, (int) watch.time(TimeUnit.SECONDS), "Mines");
                break;
            }
            else if(field.getState() == GameState.FAILED){
                update();
                System.out.println(ANSI_RED + "-------------------------");
                System.out.println("You lost :(((" + ANSI_RESET + "\n\n");

                break;
            }
        } while(true);
    }
    
    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {

        System.out.println( "------------------------------------------------");
        System.out.println("Open: O**, Mark: M**, Exit: X");
        System.out.println("** - first row (A,B...) then column (1,2...)");
        System.out.println("Number of unmark mines : " + field.getRemainingMineCount());

        boolean firstRowSpace = true;
        for(int column = 0; column < field.getColumnCount(); column++){
            if(firstRowSpace == true) {
                System.out.print(ANSI_GREEN + "+");
                for (int c = 0; c <= field.getColumnCount(); c++){
                    System.out.print("---");
                }
                System.out.println("+" + ANSI_RESET);
                System.out.print(ANSI_GREEN);
                System.out.printf("|     0  ");
                firstRowSpace = false;
            }
            else if (column < 10)
                System.out.printf(column + "  ");
            else if (column >= 10 )
                System.out.printf(column + " ");
        }
        System.out.print(ANSI_RESET);
        System.out.println();

        for(int row = 0; row < field.getRowCount(); row++){
            System.out.print(ANSI_GREEN);
            System.out.printf("| %c ", row+'A');
            System.out.print(ANSI_RESET);

            for(int column = 0; column < this.field.getColumnCount(); column++){
                Tile tile = field.getTile(row,column);

                if(tile.getState().equals(Tile.State.CLOSED) && column < 11)
                    System.out.printf("  -");
                if(tile.getState().equals(Tile.State.CLOSED) && column >= 11)
                    System.out.printf("  -");
                else if (field.getTile(row,column).getState() == Tile.State.MARKED)
                    System.out.printf(ANSI_BLUE + " M" + ANSI_RESET);
                else if ((field.getTile(row,column).getState() == Tile.State.OPEN) &&
                    field.getTile(row,column) instanceof Clue)
                    System.out.printf("  " + ((Clue) field.getTile(row,column)).getValue() );
                else if ((field.getTile(row,column).getState() == Tile.State.OPEN) &&
                        field.getTile(row,column) instanceof Mine) {
                    System.out.printf(ANSI_RED + "  X" + ANSI_RESET);
                }
            }
            System.out.println(ANSI_GREEN + " |" + ANSI_RESET);

        }
        System.out.print("+");
        for (int c = 0; c <= field.getColumnCount(); c++){
            System.out.print("---");
        }
        System.out.println("+");


    }
    
    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() throws IOException {
        int row = 0;
        int column = 0;


        System.out.println("------------------------------------------------");
        System.out.println("Enter row and column: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        try {
            handleInput(input);

        } catch (WrongFormatException e) {
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
            processInput();
        }
    }

    private void handleInput(String input) throws WrongFormatException {
        int row = 0;
        int column = 0;

        Pattern pattern = Pattern.compile("[omOM]([A-Za-z])([0-9])");
        Matcher matcher = pattern.matcher(input.toLowerCase());
        if (input.toUpperCase().equals("X")) {
            field.setState(GameState.FAILED);

        } else if (input.toUpperCase().equals("T")) {
            //System.out.println(bestTimes.toString());

        } else if (matcher.matches()) {
            row = input.toLowerCase().charAt(1) - 'a';
            column = Character.getNumericValue(input.toLowerCase().charAt(2));
            if (!(row < field.getRowCount() && column < field.getColumnCount())) {
                throw new WrongFormatException("You are out of field");
            }
            char operation = input.toLowerCase().charAt(0);
            row = input.toLowerCase().charAt(1) - 'a';
            column = Character.getNumericValue(input.toLowerCase().charAt(2));

            switch (operation) {
                case 'm':
                    field.markTile(row, column);
                    break;
                case 'o':
                    field.openTile(row, column);
                    break;
            }
        } else {
            throw new WrongFormatException("Bad expresion, try again!");
        }
    }
}




