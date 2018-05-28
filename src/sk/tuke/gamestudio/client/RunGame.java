package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.Game;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.games.hangman.Hangman;
import sk.tuke.gamestudio.games.mines.Minesweeper;
import sk.tuke.gamestudio.games.stones.Stones;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class RunGame {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static String nameOfPlayer;

    private static RatingRestServiceClient rrsc = new RatingRestServiceClient();
    private static CommentRestServiceClient crsc = new CommentRestServiceClient();
    private static ScoreRestServiceClient srsc = new ScoreRestServiceClient();
    private static Map<Integer, Game> games;

    private static void addGamesToMap(Map<Integer, Game> games){
        games.put(1, new Stones());
        games.put(2, new Minesweeper());
        games.put(3, new Hangman());

    }

    public static void main(String[] args) throws Exception {
        games = createMapOfGames();

        System.out.println(ANSI_GREEN + "-------------------------------------------------------------------------------");
        System.out.println("* * * * * * * *    S T A R T I N G   |   G a M E   S T U D i O  * * * * * * * *" + ANSI_RESET);
        System.out.println("> > > Enter your name, please: ");
        nameOfPlayer = getInput();

        while(true) {
            Game game = null;

            menuPrint();
            String input = getInput();

            switch (input){
                case "c":
                    crsc.printList(crsc.getAllComments(), null);
                    break;
                case "x":
                    return;

            }
            try {
                game = games.get(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.out.println("Bad input, try it again!");
            }
            if(game != null) {
                menuGamePrint(game);
                continue;
            }
            System.out.println("Bad input, try it again!");
        }

    }

    private static Map<Integer, Game> createMapOfGames() {
        Map<Integer, Game> games = new HashMap<>();
        addGamesToMap(games);
        return games;
    }

    private static String getInput() {
        String input = null;
        try {
                do {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    input = br.readLine();
                    if(input.equals("")){
                        System.out.println(ANSI_RED + "Try something new!" + ANSI_RESET);
                    }
                } while (input.equals(""));
            return input;
        } catch (IOException e) {
        }
        return null;
    }

    public static void rankOrCommentGame(Class clazz) {
        System.out.println(ANSI_GREEN + "You want to comment or rank game?" + ANSI_RESET);
        System.out.println("R >> rank,\nC >> comment,\nRC >> rank and comment,\nX >> no/exit");
        String input = getInput();
        switch(input.toLowerCase()){
            case "c":
                crsc.createComment(clazz, nameOfPlayer);
                break;
            case "r":
                rrsc.createRating(clazz, nameOfPlayer);
                break;
            case "rc":
                crsc.createComment(clazz, nameOfPlayer);
                rrsc.createRating(clazz, nameOfPlayer);
                break;
            case "x":
                return;
        }
    }

    public static void printWelcomeWithWait() {
        try (BufferedReader br = new BufferedReader(new FileReader("welcome.txt"))) {
            String ch;
            System.out.print(ANSI_GREEN);
            while ((ch = br.readLine()) != null) {
                System.out.println(ch);
                Thread.sleep(100);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(ANSI_RESET);
    }

    public static void menuPrint() {
        printWelcomeWithWait();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        games.entrySet().stream().forEach(m ->
                System.out.println( ANSI_GREEN +
                        m.getKey()
                        + ". " + m.getValue().getClass().getSimpleName()
                        + " ("
                        + rrsc.averageRating(m.getValue().getClass().getSimpleName()).substring(0,3)
                        + ")" + ANSI_RESET));

        System.out.println("\nCoMMeNTs: " + ANSI_GREEN + "C " + ANSI_RESET + "- get all comments ");
        System.out.println("EXiT: " + ANSI_GREEN + "X " + ANSI_RESET);
        System.out.println("Choose wisely. Your option: " + ANSI_RESET);
    }

    public static void menuGamePrint(Game game) {
        while(true) {
        Class clazz = game.getClass();
        System.out.println(ANSI_GREEN + "\n* * * * * * * * " + clazz.getSimpleName().toUpperCase() + " * * * * * * * *" + ANSI_RESET);
        System.out.println("1 >> PLAY GAME\n2 >> RaNK game\n3 >> CoMMeNT game\n" +
                "4 >> see RaNKs\n5 >> see CoMMeNTs\n6 >> HALL OF FaMe\nX >> exitOs numerOs");
        String input = getInput();
            switch (input) {
                case "1":
                    game.run();
                    if(game.getScore() != 0) {
                        Score score = new Score(nameOfPlayer, clazz.getSimpleName(), game.getScore());
                        srsc.addScore(score);
                    }
                    rankOrCommentGame(clazz);
                    break;
                case "2":
                    rrsc.createRating(clazz, nameOfPlayer);
                    break;
                case "3":
                    crsc.createComment(clazz, nameOfPlayer);
                    break;
                case "4":
                    rrsc.printList(rrsc.getAllRatingsOfGame(clazz.getSimpleName()), clazz.getSimpleName());
                    break;
                case "5":
                    crsc.printList(crsc.getAllCommentsOfGame(clazz.getSimpleName()), clazz.getSimpleName());
                    break;
                case "6":
                    srsc.printList(srsc.getBestScoresForGame(clazz.getSimpleName()),clazz.getSimpleName());
                    break;
                case "x":
                    return;
                default:
                    break;
            }
        }
    }

    public double countRating(){
        return 0d;
    }

}