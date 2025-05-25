package com.test.writeintxt;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
 
import com.test.annotation.FixedWidthFormatter;
 
public class WriteInTxt {
 
    // Overloaded method for specifying a directory
    public static <T> void writeToFile(List<T> transactions, String fileName) throws IOException {
        writeToFile(transactions, fileName, "output");  // Default directory is "output"
    }
 
    public static <T> void writeToFile(List<T> transactions, String fileName, String directoryPath) throws IOException {
        // Ensure directory exists
        Path dirPath = Paths.get(directoryPath);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
 
        // Full file path
        Path filePath = dirPath.resolve(fileName + ".txt");
 
        // Logging
        System.out.println("Transactions fetched: " + transactions.size());
        System.out.println("Writing to file: " + filePath.toAbsolutePath());
 
        // Write to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write("HDR|" + fileName + "|");
            writer.newLine();
            writer.newLine();
 
            for (T transaction : transactions) {
                String formattedTransaction = FixedWidthFormatter.formatRecord(transaction);
                writer.write(formattedTransaction);
                writer.newLine();
            }
            System.out.println("Transactions have been written to: " + filePath);
        }
    }
}