import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * KenoGuesser is a program that will try to guess what numbers are going to be the next
 * numbers by asking the user to enter the 20 numbers, putting it in a hashmap and outputs
 * x amount of numbers that appear the most (x being the amount that the user wants.)
 * 
 * The more data the better so do some practice runs so the program can be filled up with data.
 */
public class KenoGuesser {
    
    static File textfile = new File("numbers.txt");
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args){
        do { 
            importNumbers(); // Prompt the user to enter the numbers
            displayNumbers(); // Display the numbers that appear the most

            System.out.println("Would you like to keep on going? (yes or no)");
            scan.nextLine(); // Clearing the scanner

        } while (scan.nextLine().equalsIgnoreCase("yes"));
        scan.close();
    } // end main

    /**
     * inportNumbers method prompts the user to enter the 20 numbers and write them to
     * "numbers.txt" that displayNumbers() will use to see which numbers appear the most.
     * 
     * I validate the user input to be within the range of 1-80 since most keno machines 
     * chooses numbers between 1-80. 
     */
    public static void importNumbers(){

        System.out.println("Please enter the 20 numbers between 1-80:");
        int[] numbers = new int[20];
        int num; // Holds the input from the user

        for (int i = 0; i < numbers.length; i++) {

            System.out.println("Enter Number " + (i + 1) + ":");
            num = scan.nextInt();

            // While loop that validates the number is between 1-80
            while (num < 1 || num > 80) { 
                System.out.println("Error, number must be between 1-80, please enter a new number");
                num = scan.nextInt();
            }
            
            numbers[i] = num;
        }

        //FileWriter is on append mode so the text files doesn't get overriden every time the program runs
        try {
            FileWriter fw = new FileWriter(textfile, true);

            for (int i: numbers) {
                fw.write(i + "\n");
            }
            fw.close();

        } catch (IOException e) {
            // Notify the user if an error occurs where it cannot write to the file
            System.out.println("Numbers cannot be written.");
            e.printStackTrace();
        }

        System.out.println();
    } // end method

    /**
     * displayNumbers will asks the user how many of the most frequent numbers they want to display 
     * from the text file. Reads the numbers from the text file and stores them in a HashMap to count 
     * the frequency of each number. A PriorityQueue is used to sort the entries in the HashMap in 
     * decreasing order of frequency. Finally, displays the top userLength numbers that appear the most times.
     */
    public static void displayNumbers(){

        // Ask the user how many numbers they want to display
        int userLength;
        System.out.println("How many numbers do you want to display?");
        userLength = scan.nextInt();

        // Initialized the HashMap and read the numbers from the text file
        HashMap<Integer, Integer> fequentNumbers = new HashMap<>();
        try {
            FileReader fr = new FileReader(textfile);
            Scanner fileScanner = new Scanner(fr);
            int num;

            while (fileScanner.hasNextLine()) { 
                num = Integer.parseInt(fileScanner.nextLine());
                fequentNumbers.put(num, fequentNumbers.getOrDefault(num, 0)+1);
            }
            fileScanner.close();

        } catch (IOException e) { 
            // Notify the user if an error occurs when trying to read the file
            System.out.println("Error reading file.");
            e.printStackTrace();
        }
        
        // Creates the PriorityQueue to sort the entries in the HashMap by decreasing frequency
        PriorityQueue<Map.Entry<Integer,Integer>> pq = new PriorityQueue<>((a,b) -> b.getValue() - a.getValue());
        pq.addAll(fequentNumbers.entrySet());

        System.out.println("\nThe " + userLength + " numbers that appeared the most numbers are:");
        for(int i = 0; i < userLength && !pq.isEmpty(); i++){
            Map.Entry<Integer, Integer> temp = pq.poll();
            System.out.println("Number " + temp.getKey() + " appeared " + temp.getValue() + " times.");
        }

    } //end method
}// end program