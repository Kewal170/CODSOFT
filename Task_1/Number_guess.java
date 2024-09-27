import java.util.Scanner;

class Number_guess {

    // Generates a random integer between 1 and 10
    public static int randomNum() {
        return (int) (Math.random() * 10) + 1;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int num = randomNum();

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("Guess the number between 1 to 10. You have 3 tries!");

        int result = 0;
        int count = 3;

        while (result != 1 && count != 0) {
            System.out.print("\nEnter your guess: ");
            int guess = sc.nextInt();

            if (guess == num) {
                System.out.println("\n🎉 Congratulations! You guessed it right! ");
                System.out.println("You are a true guess master! ");
                result = 1;
                return;
            } else if (guess > num) {
                System.out.println(" A little lower... Try again!");
            } else if (guess < num) {
                System.out.println(" A little higher... Keep going!");
            }

            count -= 1;
            if (count > 0) {
                System.out.println("You have " + count + " tries remaining. ");
            }
        }

        if (result != 1) {
            System.out.println(" You Lost! The correct number was " + num + ". Better luck next time! ");
        }

        System.out.println("\nThanks for playing! Come back soon! ");
        sc.close(); 
    }
}
