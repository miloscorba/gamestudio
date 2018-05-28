package sk.tuke.gamestudio.games.hangman;

import sk.tuke.gamestudio.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hangman implements Game {

    private static final String WORDLIST_FILENAME = "words.txt";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private List<Character> guessWords = new ArrayList<>();
    private String secretWord;
    private int numberOfGuess = 8;
    private boolean isChange = false;

    public Hangman() {
    }

    @Override
    public void run() {
        System.out.println(ANSI_GREEN + "* * * * * * * * HANGMAN * * * * * * * * " + ANSI_RESET);
        secretWord = getWord();
        System.out.println(ANSI_GREEN + "\"Hey my friend, we are going to hang you.\"");
        System.out.println("\"But you know what? You can guess the word and we let you free...\"");
        System.out.println("\"This the WORD. Try it! You have 8 guesses.\"" + ANSI_RESET);
        while (true) {
            System.out.println(">>> " + maskTheWord(secretWord));
            getInput();
            if (!isChange) {
                numberOfGuess--;
            }
            System.out.println("------------------------------------------");
            System.out.println("Possible mistakes: " + (numberOfGuess));
            printHangMan(numberOfGuess);
            if (numberOfGuess == 0) {
                System.out.println(ANSI_RED + "You LOST. Try again. The word was:" + ANSI_RESET);
                System.out.println(secretWord.toUpperCase());
                return;
            }
            if (isSolved()) {
                System.out.println("--------------------------------------");
                System.out.println("Ohh maaan, thats correct, it was: " + secretWord.toUpperCase());
                System.out.println("Youre free -_-");
                System.out.println("--------------------------------------");
                return;
            }
        }
    }

    private String getWord() {
        try {
            FileInputStream fs = new FileInputStream(WORDLIST_FILENAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            ArrayList<String> array = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null)
                array.add(line);
            Random rand = new Random();
            int randomIndex = rand.nextInt(array.size());

            System.out.println(">>>" + array.get(randomIndex));

            return array.get(randomIndex);
        } catch (IOException e) {
            System.out.println("Secret word load FAILED!");
        }
        return null;
    }

    private void getInput() {
        isChange = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        while (true) {

            System.out.println("Enter word to guess: ");
            try {
                input = br.readLine();

            } catch (IOException e) {
                System.out.println("Not a valid enter. Try again!");
            }
            Pattern pattern = Pattern.compile("[a-z]");
            Matcher matcher = pattern.matcher(input.toLowerCase());
            if (!matcher.matches()) {
                System.out.println("Not a valid enter. Try again!");
                continue;
            }
            if (!guessWords.contains(input.charAt(0))) {
                guessWords.add(input.toLowerCase().charAt(0));
                if (secretWord.contains(input.toLowerCase())) {
                    isChange = true;
                }
                return;
            }

            System.out.println("You already try this word! Try again!");
        }
    }

    private String maskTheWord(String secretWord) {
        String toPrint = "";
        for (int wordSequence = 0; wordSequence < secretWord.length(); wordSequence++) {
            if (!guessWords.contains(secretWord.toLowerCase().charAt(wordSequence))) {
                toPrint = toPrint.concat("_ ");
            } else {
                toPrint = toPrint.concat(secretWord.toUpperCase().charAt(wordSequence) + " ");
            }
        }
        return toPrint;
    }

    private boolean isSolved() {
        for (int i = 0; i < secretWord.length(); i++) {
            if (!guessWords.contains(secretWord.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double getScore() {
        return numberOfGuess * 10 + secretWord.length() * 10;
    }

    private void printHangMan(int numberOfGuess) {

        switch (numberOfGuess) {
            case 7:
                System.out.println(ANSI_GREEN + "\"Be carefull my friend, gallows is growing!\"");
                System.out.println(" \n\n\n\n\n\n\n---" + ANSI_RESET);
                break;
            case 6:
                System.out.println(ANSI_GREEN + "\"Thats a lot of wood, but we are going to build it!\"");
                System.out.println(" \n|\n|\n|\n|\n|\n|\n---" + ANSI_RESET);
                break;
            case 5:
                System.out.println(ANSI_GREEN + "\"This is so easy to buid it, easier than your guesses!\"");
                System.out.println("--------\n|\n|\n|\n|\n|\n|\n---" + ANSI_RESET);
                break;
            case 4:
                System.out.println(ANSI_GREEN + "\"Houpe houpe houpe, we have the rope!\"");
                System.out.println("--------\n|      |\n|\n|\n|\n|\n|\n---" + ANSI_RESET);
                break;
            case 3:
                System.out.println(ANSI_GREEN + "\"Oh my good I see the head!\"");
                System.out.println("--------\n|      |\n|      O\n|\n|\n|\n|\n---" + ANSI_RESET);
                break;
            case 2:
                System.out.println(ANSI_GREEN + "\"Ou yeah, come to mama...\"");
                System.out.println("--------\n|      |\n|      O\n|     /|\\\n|\n|\n|\n---" + ANSI_RESET);
                break;
            case 1:
                System.out.println(ANSI_GREEN + "\"What will be your last words?\"");
                System.out.println("--------\n|      |\n|      O\n|     /|\\\n|      |\n|\n|\n---" + ANSI_RESET);
                break;
            case 0:
                System.out.println(ANSI_GREEN + "--------\n|      |\n|      O\n|     /|\\\n|      |\n|     /*\\\n|\n---" + ANSI_RESET);
                break;
        }
    }

}

