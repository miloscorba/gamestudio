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
    private String wordToPrint = "";
    private int numberOfGuess = 0;
    private boolean isChange = false;

    @Override
    public void run()  {
        System.out.println(ANSI_GREEN + "* * * * H a N G   M a N * * * * " + ANSI_RESET);
        secretWord = getWord();
        while(true) {
            System.out.println(maskTheWord(secretWord));
            getInput();
            if(isSolved())
                return;
            if(!isChange) {
                numberOfGuess++;
            }
            printHangMan(numberOfGuess);
            if(numberOfGuess == 8) {
                System.out.println(ANSI_RED + "You LOST. Try again. The word was:" + ANSI_RESET);
                System.out.println(secretWord.toUpperCase());
                return;
            }
        }
    }

    private String getWord() {
        try {
            FileInputStream fs= new FileInputStream(WORDLIST_FILENAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            ArrayList<String> array = new ArrayList<>();
            String line;
            while((line = br.readLine()) != null)
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

    private void getInput(){
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
            if(!matcher.matches())
                System.out.println("Not a valid enter. Try again!");
            if(!guessWords.contains(input.charAt(0))){
                guessWords.add(input.charAt(0));
                if(secretWord.contains(input)){
                    isChange = true;
                    return;
                }
            }

            System.out.println("You already try this word! Try again!");
        }
    }


    private String maskTheWord(String secretWord){
        String toPrint = "";
        for(int wordSequence = 0; wordSequence < secretWord.length(); wordSequence++){
            if(!guessWords.contains(secretWord.toLowerCase().charAt(wordSequence))){
                toPrint = toPrint.concat("_ ");
            } else {
                toPrint = toPrint.concat(secretWord.toUpperCase().charAt(wordSequence) + " ");
            }
        }
        return toPrint;
    }



    private boolean isSolved(){
        return secretWord.equals(wordToPrint.replaceAll("\\s+",""));
    }

    @Override
    public double getScore() {
        return 0;
    }

    private void printHangMan(int numberOfGuess){
        System.out.println("You have " + numberOfGuess + " out of 8.");
        switch(numberOfGuess){
            case 1:
                System.out.println(ANSI_GREEN + " \n\n\n\n\n\n\n---" + ANSI_RESET);
                break;
            case 2:
                System.out.println(ANSI_GREEN + " \n|\n|\n|\n|\n|\n|\n---" + ANSI_RESET);
                break;
            case 3:
                System.out.println(ANSI_GREEN + "--------\n|\n|\n|\n|\n|\n|\n---" + ANSI_RESET);
                break;
            case 4:
                System.out.println(ANSI_GREEN + "--------\n|\n|\n|\n|\n|\n|\n---" + ANSI_RESET);
                break;
            case 5:
                System.out.println(ANSI_GREEN + "--------\n|      |\n|\n|\n|\n|\n|\n---" + ANSI_RESET);
                break;
            case 6:
                System.out.println(ANSI_GREEN + "--------\n|      |\n|      O\n|\n|\n|\n|\n---" + ANSI_RESET);
                break;
            case 7:
                System.out.println(ANSI_GREEN + "--------\n|      |\n|      O\n|     /|\\\n|\n|\n|\n---" + ANSI_RESET);
                break;
            case 8:
                System.out.println(ANSI_GREEN + "--------\n|      |\n|      O\n|     /|\\\n|      |\n|     /*\\\n|\n---"  + ANSI_RESET);
                break;
        }
    }

}

