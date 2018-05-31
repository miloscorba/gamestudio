package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.forJson.book.BookInfo;
import sk.tuke.gamestudio.games.Game;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.games.TimeWatch;
import sk.tuke.gamestudio.games.hangman.Hangman;
import sk.tuke.gamestudio.games.mines.Minesweeper;
import sk.tuke.gamestudio.games.stones.Stones;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.mail.MessagingException;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RunGame {
    @Resource(lookup = "jms/achievementQueue")
    private Queue queue;
    //C O L O R s
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private static RatingRestServiceClient rrsc = new RatingRestServiceClient();
    private static CommentRestServiceClient crsc = new CommentRestServiceClient();
    private static ScoreRestServiceClient srsc = new ScoreRestServiceClient();
    private static WeatherRestServiceClient wrsc = new WeatherRestServiceClient();

    private static String nameOfPlayer;
    private static Map<Integer, Game> games;
    private static String book;

    private void addGamesToMap(Map<Integer, Game> games) {
        games.put(1, new Stones());
        games.put(2, new Minesweeper());
        games.put(3, new Hangman());

    }

    public static void main(String[] args) throws Exception {

        RunGame runGame = new RunGame();
        runGame.run();

    }

    private void sendEmail(String email) {
        String[] recipients = new String[]{email};
        String subject = "You're winnig the BOOK!";
        String messageBody = "Hello " + nameOfPlayer + ",\ndue to beating the first place youre becoming a LEGEND.\n" +
                "Thanks to that, youre getting from us a gift:\n\n" +
                "The BooK: " + book +
                "\n\nThanks you and see you soon...\n Yours GameStudio!";
        new MailUtil().sendMail(recipients, subject, messageBody);
    }

    private void chooseBook() {
        System.out.println(ANSI_GREEN + "If you beat the best time in one of the game,");
        System.out.println("you can win a book of your choosing by some topic." + ANSI_RESET);
        System.out.println("You can write author or title: ");
        String inputForBook = getInput();
        inputForBook = inputForBook.replaceAll("\\s+","");
        BookRestServiceClient brsc = new BookRestServiceClient();
        BookInfo bi = brsc.getBookInfo(inputForBook);
        System.out.println(bi.totalItems);
        Random random = new Random();
        int number = random.nextInt(bi.items.size());

        while(bi.items.get(number).volumeInfo.title == null ||  bi.items.get(number).volumeInfo.authors == null) {
            System.out.println("Try something new please.");
            inputForBook = getInput();
            inputForBook = inputForBook.replaceAll("\\s+","");
            bi = brsc.getBookInfo(inputForBook);
            number = random.nextInt(bi.items.size());
        }
        book = bi.items.get(number).volumeInfo.title + " by " + bi.items.get(number).volumeInfo.authors.toString();
        System.out.println("-------------------------------------------------------------");
        System.out.println("We choose you this book: ");
        System.out.println(book);
        System.out.println("--------------------------------------------------------------");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }

    }

    private void run() {
        games = createMapOfGames();

        System.out.println(ANSI_GREEN + "-------------------------------------------------------------------------------");
        System.out.println("* * * * * * * *    S T A R T I N G   |   G a M E   S T U D i O  * * * * * * * *" + ANSI_RESET);
        System.out.println("> > > Enter your name, please: ");
        nameOfPlayer = getInput();
        chooseBook();
        while (true) {
            Game game = null;

            printBaseMenu();
            String input = getInput();

            switch (input) {
                case "c":
                    printCommentList(crsc.getAllComments(), null);
                    continue;
                case "x":
                    return;

            }
            try {
                game = games.get(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.out.println("Bad input, try it again!");
            }
            if (game != null) {
                printGameMenu(game);
                continue;
            }
            System.out.println("Bad input, try it again!");
        }
    }

    private Map<Integer, Game> createMapOfGames() {
        Map<Integer, Game> games = new HashMap<>();
        addGamesToMap(games);
        return games;
    }

    private void rankOrCommentGame(String gameName) {
        System.out.println(ANSI_GREEN + "You want to comment or rank game?" + ANSI_RESET);
        System.out.println("R >> rank,\nC >> comment,\nRC >> rank and comment,\nX >> no/exit");
        String input = getInput();
        switch (input.toLowerCase()) {
            case "c":
                createComment(gameName, nameOfPlayer);
                break;
            case "r":
                createRating(gameName, nameOfPlayer);
                break;
            case "rc":
                createComment(gameName, nameOfPlayer);
                createRating(gameName, nameOfPlayer);
                break;
            case "x":
                return;
        }
    }

    private void printWelcomeWithWait() {
        try (BufferedReader br = new BufferedReader(new FileReader("welcome.txt"))) {
            String ch;
            System.out.print(ANSI_GREEN);
            while ((ch = br.readLine()) != null) {
                System.out.println(ch);
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(ANSI_RESET);
    }

    private void printBaseMenu() {
        printWelcomeWithWait();

        games.entrySet().stream().forEach(m ->
                System.out.println(ANSI_GREEN +
                        m.getKey()
                        + ". " + m.getValue().getName()
                        + " ("
                        + rrsc.averageRating(m.getValue().getName()).substring(0, 3)
                        + ")" + ANSI_RESET));

        System.out.println("\nCoMMeNTs: " + ANSI_GREEN + "C " + ANSI_RESET + "- get all comments ");
        System.out.println("EXiT: " + ANSI_GREEN + "X " + ANSI_RESET);
        System.out.println("Choose wisely. Your option: " + ANSI_RESET);
    }

    private void printGameMenu(Game game) {
        while (true) {
            String gameName = game.getName();
            System.out.println(ANSI_GREEN + "\n* * * * * * * * " + gameName.toUpperCase() + " * * * * * * * *" + ANSI_RESET);
            System.out.println("1 >> PLAY GAME\n2 >> RaNK game\n3 >> CoMMeNT game\n" +
                    "4 >> see RaNKs\n5 >> see CoMMeNTs\n6 >> HALL OF FaMe\nX >> exitOs numerOs");
            String input = getInput();
            switch (input) {
                case "1":
                    game.run();
                    createScore(game, gameName);
                    rankOrCommentGame(gameName);
                    break;
                case "2":
                    createRating(gameName, nameOfPlayer);
                    break;
                case "3":
                    createComment(gameName, nameOfPlayer);
                    break;
                case "4":
                    printRankList(rrsc.getAllRatingsOfGame(gameName), gameName);
                    break;
                case "5":
                    printCommentList(crsc.getAllCommentsOfGame(gameName), gameName);
                    break;
                case "6":
                    printScoreList(srsc.getBestScoresForGame(gameName), gameName);
                    break;
                case "x":
                    return;
                default:
                    break;
            }
        }
    }

    private void createScore(Game game, String gameName) {
        if (game.getScore() != 0) {
            Score score = new Score(nameOfPlayer, gameName, game.getScore());
            List<Score> scores = srsc.getBestScoresForGame(gameName);
            if (!scores.isEmpty()) {
                System.out.println(scores.get(0).getScore() + " :: " + score.getScore());
                if (scores.get(0).getScore() < score.getScore()) {
                    createEmail();
                }
            } else {
                createEmail();
            }
                srsc.addScore(score);
        }
    }

    private void createEmail() {
        System.out.println("Thats AMAZING! You are winning the BOOK !");
        System.out.println("Enter your email here: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String email = null;
        try {
            email = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendEmail(email);
    }

    private void createComment(String gameName, String nameOfPlayer){
        Comment comment = new Comment();
        System.out.println("Your Comment: ");
        comment.setComment(getInput());
        comment.setGame(gameName);
        comment.setName(nameOfPlayer);
        crsc.addComment(comment);
    }

    private void createRating(String gameName, String nameOfPlayer) {
        Rating rating = new Rating();
        int rate = -1;
        while (true) {
            System.out.println("Put your raking here: (between 1 and 5)");
            Pattern p = Pattern.compile("[12345]");
            String input = getInput();
            Matcher m = p.matcher(input);
            if (!m.matches()) {
                System.out.println(ANSI_RED + "Not a valid rating!" + ANSI_RESET);
                continue;
            }
            rate = Integer.parseInt(input);
            if (rate <= 5 && rate >= 1) {
                rating.setRate(rate);
                rating.setGame(gameName);
                rating.setName(nameOfPlayer);
                rrsc.setRating(rating);
                return;
            } else {
                System.out.println(ANSI_RED + "Not a valid rating!" + ANSI_RESET);
            }
        }
    }

    private void printRankList(List<Rating> list, String game) {
        int index = 1;
        if (game != null) {
            System.out.println(ANSI_GREEN + "- - - - R a T i N G S  of " + game.toUpperCase() + " G a M E - - - - " + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN + "- - - - R a T i N G S - - - -" + ANSI_RESET);
        }
        for (Rating rating : list) {
            System.out.println(index + ". "
                    + rating.getName() + ": "
                    + rating.getRate());
            index++;
        }
    }

    private void printCommentList(List<Comment> list, String game){
        int index = 1;
        if(game != null){
            System.out.println(ANSI_GREEN + "- - - - C o M e N T S  of " + game.toUpperCase() + " G a M E - - - - " + ANSI_RESET);
            for(Comment comment : list){
                System.out.println(index + ". "
                        + comment.getName() + ": "
                        + comment.getComment());
                index++;
            }
            System.out.println();
        } else {
            System.out.println(ANSI_GREEN +"- - - - C o M e N T S - - - -" + ANSI_RESET);
            for(Comment comment : list){
                System.out.println(index + ". "
                        + comment.getName()
                        + " (" + comment.getGame() + ")"
                        + ": " + comment.getComment());
                index++;
            }
            System.out.println();
        }
    }

    private void printScoreList(List<Score> list, String game) {
        int index = 1;
        if (game != null) {
            boolean first = true;
            System.out.println(ANSI_GREEN + "- - - " + game + " H a L L  of  F a M e - - -" + ANSI_RESET);
            for (Score score : list) {
                if (first) {
                    System.out.println("------ THe BeST ------");
                }
                System.out.println(index + ". " + score.getPlayer() + ": " + score.getScore());
                if (first) {
                    System.out.println("----------------------");
                }
                first = false;
                index++;
            }
        }
    }

    private String getInput() {
        String input = null;
        try {
            do {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                input = br.readLine();
                if (input.equals("")) {
                    System.out.println(ANSI_RED + "Try something new!" + ANSI_RESET);
                }
            } while (input.equals(""));
            return input;
        } catch (IOException e) {
        }
        return null;
    }


}