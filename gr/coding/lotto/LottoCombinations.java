package gr.coding.lotto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This program generates all possible combinations of 6 numbers from a given set of numbers
 * read from an input file. It validates each combination against specific filtering criteria
 * and writes the valid combinations to an output file.
 *
 * <p>Main Features:
 * <ul>
 *     <li>Reads unique numbers between 1 and 49 from an input file.</li>
 *     <li>Filters out invalid inputs (duplicates, out-of-range numbers, or non-integer tokens).</li>
 *     <li>Generates combinations of 6 numbers using recursion.</li>
 *     <li>Applies multiple validation checks on combinations:
 *         <ul>
 *             <li>Ensures no more than 4 even numbers in a combination.</li>
 *             <li>Ensures no more than 4 odd numbers in a combination.</li>
 *             <li>Limits consecutive numbers to a maximum of 2.</li>
 *             <li>Ensures no more than 3 numbers with the same last digit.</li>
 *             <li>Limits numbers belonging to the same tens group (e.g., 10-19) to a maximum of 3.</li>
 *         </ul>
 *     </li>
 *     <li>Outputs valid combinations to a text file.</li>
 * </ul>
 * </p>
 *
 * <p>Usage:
 * Ensure the input file `input.txt` contains between 7 and 49 unique numbers (one per line or space-separated).
 * The program writes valid combinations to `lotto-combinations.txt`.</p>
 *
 * <p>Note: This program assumes a working directory where the input and output files are accessible.</p>
 *
 * @author Kyriakos Koutsourelis
 */
public class LottoCombinations {
    static int validCombinationCount = 0;

    public static void main(String[] args) {
        File inputLottoNumbers = new File("input.txt");
        File lottoCombinations = new File("lotto-combinations.txt");

        try (Scanner in = new Scanner(inputLottoNumbers)) {
            int[] numbers = new int[49];
            int count = 0;

            while (in.hasNext()) { // Process every token in the input
                if (in.hasNextInt()) { // Check if the next token is an integer
                    int lottoNumber = in.nextInt();
                    if (lottoNumber >= 1 && lottoNumber <= 49) {
                        // Check if the number is already in the array
                        boolean isDuplicate = false;
                        for (int i = 0; i < count; i++) {
                            if (numbers[i] == lottoNumber) { // If duplicate found, exit loop
                                isDuplicate = true;
                                break;
                            }
                        }
                        if (!isDuplicate) { // Add number if not a duplicate
                            numbers[count++] = lottoNumber;
                        } else {
                            System.out.println("Duplicate number " + lottoNumber + " found and skipped.");
                        }
                    } else {
                        System.out.println("Skipping out-of-range number: " + lottoNumber);
                    }
                } else { // If the token is not an integer
                    System.out.println("Skipping invalid input: " + in.next()); // Consume and skip non-integer
                }
            }

            // Check if total numbers in input.txt are less than 7 or more than 49
            if (count < 7 || count > 49) {
                System.out.println("The file input.txt must contain minimum 7 and maximum 49 numbers unique numbers!");
                return;
            }

            // Trim the array to the size of input numbers
            numbers = Arrays.copyOf(numbers, count);

            // Sort the array
            Arrays.sort(numbers);

            // Calculate total number of combinations
            int totalCombinations = calculateNumberOfCombinations(count, 6);
            System.out.println("Total combinations before filtering: " + totalCombinations);

            // Generate and write valid combinations to output file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(lottoCombinations))) {
                generateCombinations(numbers, new int[6], 0, 0, writer);
            }
            // Check if any valid combinations were found
            if (validCombinationCount == 0) {
                System.out.println("There were no combinations after filtering!");
            } else {
                System.out.println("Total combinations after filtering: " + validCombinationCount);
            }
            System.out.println("All valid combinations are written to file lotto-combinations.txt");


        } catch (IOException e) { // Handle file read/write errors
            e.printStackTrace();
        }
    }

    // Check if combination has more than 4 Evens
    public static boolean hasTooManyEvens(int[] arr) {
        int counter = 0;
        for (int num : arr) {
            if (num % 2 == 0) counter++;
        }
        return counter > 4;
    }

    // Check if combination has more than 4 Odds
    public static boolean hasTooManyOdds(int[] arr) {
        int counter = 0;
        for (int num : arr) {
            if (num % 2 != 0) counter++;
        }
        return  counter > 4;
    }

    // Check if combination has more than 2 Consecutive numbers
    public static boolean hasTooManyConsecutive(int[] arr) {
        int counter = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == arr[i + 1] - 1) counter++;
        }
        return counter > 2;
    }

    // Check if combination has more than 3 numbers with the same ending digit
    public static boolean hasTooManySameEndings(int[] arr) {
        int[] lastDigits = new int[10]; // Array to count occurrences of each ending digit (0-9)

        for (int num : arr) {
            lastDigits[num % 10]++;
        }

        for (int num : lastDigits) {
            if (num > 3) return true;
        }
        return false;
    }

    // Check if combination has more than 3 numbers belonging to the same tens group
    public static boolean hasTooManySameTens(int[] arr) {
        int[] tens = new int[5]; // Array to count occurrences of each tens group (0-4)

        for (int num : arr) {
            tens[num / 10]++;
        }

        for (int num : tens) {
            if (num > 3) return true;
        }
        return false;
    }

    // Calculate factorial of number
    public static int factorial(int n) {
        int fact = 1;
        if (n == 0 || n == 1) return 1;
        return n * factorial(n - 1);
    }

    // Calculate the number of combinations (nCr = n! / (r! * (n-r)!))
    public static int calculateNumberOfCombinations(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    // Generate all combinations of 6 numbers recursively
    private static void generateCombinations(int[] numbers, int[] combination, int start, int index, BufferedWriter writer) throws IOException {
        // Base case: When the current combination is complete (contains 6 numbers)
        if (index == 6) {
            if (isValidCombination(combination)) {  // Check if the combination passes all the filtering criteria
                // If valid, write the combination to the output file
                writer.write(Arrays.toString(combination));
                writer.newLine();
                validCombinationCount++; // Increment the count of valid combinations
            }
            return; // Exit this recursive call
        }

        // Recursive loop: Generate combinations starting from the current position
        for (int i = start; i < numbers.length; i++) {
            // Place the current number in the combination array at the current index
            combination[index] = numbers[i];

            // Recursively generate combinations with the next number
            // - `i + 1` ensures no duplicate numbers are used in a combination
            // - `index + 1` moves to the next position in the combination array
            generateCombinations(numbers, combination, i + 1, index + 1, writer);
        }
    }

    // Validate a combination against all criteria
    public static boolean isValidCombination(int[] combination) {
        return !hasTooManyEvens(combination) && !hasTooManyOdds(combination) && !hasTooManyConsecutive(combination)
                && !hasTooManySameEndings(combination) && !hasTooManySameTens(combination);
    }
}