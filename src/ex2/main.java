package ex2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до вхідного файлу: ");
        String inputPath = scanner.next();
        File inputFile = new File(inputPath);
        while (!inputFile.exists()) {
            System.out.print("Файл не знайдено. Введіть коректний шлях: ");
            inputPath = scanner.next();
            inputFile = new File(inputPath);
        }

        System.out.print("Введіть шлях до вихідного файлу: ");
        String outputPath = scanner.next();
        File outputFile = new File(outputPath);

        if (!outputFile.exists()) {
            System.out.print("Файл не знайдено. Створити новий файл? (y/n): ");
            String createNewFile = scanner.next();
            if (createNewFile.equalsIgnoreCase("y")) {
                try {
                    outputFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().replaceAll("\\s+", " ");
                if (!line.isEmpty()) {
                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
