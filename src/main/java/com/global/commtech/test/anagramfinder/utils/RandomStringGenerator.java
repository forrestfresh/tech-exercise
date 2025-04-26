package com.global.commtech.test.anagramfinder.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomStringGenerator {

    public static void main(String[] args) {
        String filePath = "random_strings.txt";  // Output file
        int numberOfStrings = 10_000;  // Total number of strings to generate (e.g., 10 million)

        // Charset to choose random characters from
        String chars = "abcdefgh";

        Random random = new Random();

        // Create the file
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            int totalGenerated = 0;

            // Loop through string lengths from 3 to 10
            for (int length = 2; length <= 12; length++) {
                // Calculate how many strings to generate for this length
                int countForThisLength = numberOfStrings / 8;  // Distribute strings roughly evenly across lengths

                for (int i = 0; i < countForThisLength; i++) {
                    // Generate the random string of current length
                    StringBuilder sb = new StringBuilder(length);
                    for (int j = 0; j < length; j++) {
                        sb.append(chars.charAt(random.nextInt(chars.length())));
                    }

                    // Write the string to the file, followed by a newline
                    writer.write(sb.toString());
                    writer.newLine();

                    totalGenerated++;
                    if (totalGenerated >= numberOfStrings) {
                        break;
                    }
                }

                if (totalGenerated >= numberOfStrings) {
                    break;
                }
            }

            System.out.println("Random strings generated and written to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
