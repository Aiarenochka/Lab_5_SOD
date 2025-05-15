package main;

import main.hashTable.HashTable;

import java.util.*;

public class Main {
    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        HashTable hashTable = new HashTable(8);

        while (true) {
            System.out.print("""
                    Menu:
                    1. Add new element
                    2. Delete element
                    3. Is the element in the table?
                    4. Display elements
                    5. Time comparison
                    0. Exit
                    Your option >>\s""");

            switch (sc.nextInt()) {
                case 1 -> hashTable.insert(sc.next());
                case 2 -> hashTable.delete(sc.next());
                case 3 -> System.out.println(hashTable.search(sc.next()) ? "Yes" : "No");
                case 4 -> hashTable.display();
                case 5 -> timeComparison();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void timeComparison() {
        final int ELEMENT_COUNT = 1_000_000;
        final int SEARCH_COUNT = 10_000;

        List<String> testData = generateRandomStrings(ELEMENT_COUNT, 10);
        List<String> searchKeys = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < SEARCH_COUNT; i++) {
            searchKeys.add(testData.get(rand.nextInt(ELEMENT_COUNT)));
        }

        HashTable myTable = new HashTable(ELEMENT_COUNT * 2);

        long startInsert = System.currentTimeMillis();
        for (String str : testData) {
            myTable.insert(str);
        }
        long endInsert = System.currentTimeMillis();

        long startSearch = System.currentTimeMillis();
        for (String key : searchKeys) {
            myTable.search(key);
        }
        long endSearch = System.currentTimeMillis();

        HashSet<String> javaSet = new HashSet<>(ELEMENT_COUNT);

        long startJavaInsert = System.currentTimeMillis();
        javaSet.addAll(testData);
        long endJavaInsert = System.currentTimeMillis();

        long startJavaSearch = System.currentTimeMillis();
        for (String key : searchKeys) {
            javaSet.contains(key);
        }
        long endJavaSearch = System.currentTimeMillis();

        System.out.println("\nРезультати тестування:");
        System.out.printf("Моя реалізація:\n  Додавання: %d мс\n  Пошук: %d мс\n",
                (endInsert - startInsert), (endSearch - startSearch));
        System.out.printf("HashSet:\n  Додавання: %d мс\n  Пошук: %d мс\n",
                (endJavaInsert - startJavaInsert), (endJavaSearch - startJavaSearch));
    }

    public static List<String> generateRandomStrings(int count, int length) {
        List<String> strings = new ArrayList<>();
        Random random = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < count; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < length; j++) {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }
            strings.add(sb.toString());
        }
        return strings;
    }
}
