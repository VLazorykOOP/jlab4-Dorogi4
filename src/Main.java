import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть шлях до вхідного файлу:");
        String inputFilePath = scanner.nextLine();

        String[] lines = new String[1000];
        int lineCount = 0;

        while (lineCount == 0) {
            try {
                lines = Files.readAllLines(Paths.get(inputFilePath)).toArray(lines);
                lineCount = lines.length;
            } catch (IOException e) {
                System.err.println("Файл не знайдений. Введіть шлях повторно:");
                inputFilePath = scanner.nextLine();
            }
        }

        System.out.println("Введіть шлях до вихідного файлу (список чисел):");
        String outputPathNumbers = scanner.nextLine();

        System.out.println("Введіть шлях до іншого вихідного файлу (рядки без цифр):");
        String outputPathNoDigits = scanner.nextLine();

        String[] linesWithoutDigits = new String[1000];
        int[] digits = new int[1000];
        int linesWithoutDigitsCount = 0;
        int digitsCount = 0;

        for (String line : lines) {
            if (line == null) continue;
            boolean hasDigits = line.matches(".*\\d+.*");
            if (hasDigits) {
                String[] separatedDigits = line.replaceAll("[^0-9]", " ").split("\\s+");
                for (String digit : separatedDigits) {
                    if (!digit.isEmpty()) {
                        digits[digitsCount++] = Integer.parseInt(digit);
                    }
                }
            } else {
                linesWithoutDigits[linesWithoutDigitsCount++] = line;
            }
        }

        Arrays.sort(digits, 0, digitsCount);

        int totalSum = 0;
        StringBuilder digitStr = new StringBuilder();
        for (int i = 0; i < digitsCount; i++) {
            int digit = digits[i];
            totalSum += digit;
            if (digitStr.length() > 0) {
                digitStr.append(", ");
            }
            digitStr.append(digit);
        }

        System.out.println("Сума чисел: " + totalSum);
        System.out.println("Доданки: " + digitStr);

        try {
            Files.newBufferedReader(Paths.get(outputPathNoDigits));
        } catch (IOException e) {
            System.err.println("Файл не знайдений. Створити новий файл? (Y/N):");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("Y")) {
                try {
                    Files.createFile(Paths.get(outputPathNoDigits));
                } catch (IOException ex) {
                    System.err.println("Помилка при створенні файлу.");
                }
            } else if (response.equalsIgnoreCase("N")) {
                System.exit(0);
            }
        }

        try {
            Files.newBufferedReader(Paths.get(outputPathNumbers));
        } catch (IOException e) {
            System.err.println("Файл не знайдений. Створити новий файл? (Y/N):");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("Y")) {
                try {
                    Files.createFile(Paths.get(outputPathNumbers));
                } catch (IOException ex) {
                    System.err.println("Помилка при створенні файлу.");
                }
            } else if (response.equalsIgnoreCase("N")) {
                System.exit(0);
            }
        }

        try (BufferedWriter writerNoDigits = Files.newBufferedWriter(Paths.get(outputPathNoDigits))) {
            for (int i = 0; i < linesWithoutDigitsCount; i++) {
                String line = linesWithoutDigits[i];
                writerNoDigits.write(line);
                writerNoDigits.newLine();
            }
        } catch (IOException e) {
            System.err.println("Помилка при записі до файлу");
        }

        try (BufferedWriter writerNums = Files.newBufferedWriter(Paths.get(outputPathNumbers))) {
            for (int i = 0; i < digitsCount; i++) {
                writerNums.write(String.valueOf(digits[i]));
                writerNums.newLine();
            }
        } catch (IOException e) {
            System.err.println("Помилка при записі до файлу");
        }
    }
}
