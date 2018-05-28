package sk.tuke.gamestudio.games.hangman;

public class Main {
    private static final String WORDLIST_FILENAME = "words.txt";
    private static final String WORDLIST_FILENAME22 = "words22.txt";
    public static void main(String[] args) throws  Exception{
        Hangman hangman = new Hangman();
        hangman.run();
    }


}

