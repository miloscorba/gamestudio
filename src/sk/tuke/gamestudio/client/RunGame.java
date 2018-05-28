package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.Game;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.games.mines.Minesweeper;
import sk.tuke.gamestudio.games.stones.Settings.TimeWatch;
import sk.tuke.gamestudio.games.stones.Stones;

import java.io.*;
import java.util.concurrent.TimeUnit;


public class RunGame {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static String nameOfPlayer;

    public static RatingRestServiceClient rrsc = new RatingRestServiceClient();
    public static CommentRestServiceClient crsc = new CommentRestServiceClient();
    public static ScoreRestServiceClient srsc = new ScoreRestServiceClient();

    public static void main(String[] args) throws Exception {

        System.out.println(ANSI_GREEN + "-------------------------------------------------------------------------------");
        System.out.println("* * * * * * * *    S T A R T I N G   |   G a M E   S T U D i O  * * * * * * * *" + ANSI_RESET);
        System.out.println("> > > Enter your name, please: ");
        nameOfPlayer = getInput();
        System.out.flush();

        while(true) {
            menuPrint();
            String input = getInput();
            Game game;
            switch (input.toLowerCase().charAt(0)) {
                case '1':
                    game = new Stones();
                    menuGamePrint(game);
                    break;
                case '2':
                    game = new Minesweeper();
                    menuGamePrint(game);
                    break;
                case 'c':
                    crsc.printList(crsc.getAllComments(), null);
                    break;
                case 'x':
                    System.out.println(ANSI_GREEN + ">>>>>>>>>> G O O D   B Y E <<<<<<<<<" +ANSI_RESET);
                    return;
                default:
                    System.out.println(ANSI_RED + "Choose again, because you choose WRONG!" + ANSI_RESET);
            }
        }
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
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nG A M E S  (rating) :\n" + ANSI_GREEN
                + "1 >> Stones (" + rrsc.averageRating("Stones").substring(0,3) + ")" + "\n"
                + "2 >> Minesweeper (" + rrsc.averageRating("Minesweeper").substring(0,3) + ")" + "\n"
                + ANSI_RESET);
        System.out.println("CoMMeNTs: " + ANSI_GREEN + "C " + ANSI_RESET + "- get all comments ");
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