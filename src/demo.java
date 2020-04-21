import java.util.Random;
import java.util.Scanner;

public class demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int integerValue = random.nextInt(200) + 1;
        System.out.println(integerValue);

        System.out.println("********************************");
        System.out.println("*    FIND THE SECRET NUMBER    *");
        System.out.println("********************************\n");

        String userName;
        do {
            System.out.print("Enter your name to proceed : ");
            userName = sc.next();

            if (userName.length() < 30) {
                break;
            } else {
                System.out.println("Player name should be lower than 30 characters!");
                System.out.print("Enter your name to proceed : ");
                userName = sc.next();
            }
        } while (!userName.equals(""));

        System.out.println("\n************ Instructions ***********");
        System.out.println("You have 10 turns to guess the number");
        System.out.println("*************************************");

        int turns = 10;
        while (turns != 0) {
            System.out.print("\nGuess a number : ");
            while (!sc.hasNextInt()) {
                System.out.print("Prompt integers only!");
                System.out.print("\n\nGuess a number : ");
                sc.next();
            }
            int guessedNumber = sc.nextInt();

            if (integerValue > guessedNumber) {
                System.out.println("\nHigher! Higher!!");

            } else if (integerValue < guessedNumber) {
                System.out.println("\nLower! Lower!!");

            } else {
                System.out.println("\nCongratulations! " + userName + ", won the game!");
                break;
            }
            turns--;
            System.out.println("You have " + turns + " turns more..");
            System.out.println("\n------------------------------------------------");
        }
    }
}
