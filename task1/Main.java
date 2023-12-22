package ru.mirea.lab31.task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProcessorTree processorTree = new ProcessorTree();

        URL url = Main.class.getResource("PROCS.txt");
        try (Scanner scanner = new Scanner(new File(url.getPath()))) {
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(", ");
                int key = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double clockFrequency = Double.parseDouble(tokens[2]);
                int cacheSize = Integer.parseInt(tokens[3]);
                double busFrequency = Double.parseDouble(tokens[4]);
                int specInt = Integer.parseInt(tokens[5]);
                int specFp = Integer.parseInt(tokens[6]);

                Processor processor = new Processor(key, name, clockFrequency, cacheSize, busFrequency, specInt, specFp);
                processorTree.insert(processor);
            }
            System.out.println("Data loaded from file successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scanner userInput = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nEnter a command (L, D n, A n, S, E): ");
            String command = userInput.next();

            switch (command) {
                case "L":
                    System.out.println("\nProcessor Tree Nodes:");
                    processorTree.printLevels();
                    break;
                case "D":
                    int deleteKey = userInput.nextInt();
                    processorTree.delete(deleteKey);
                    break;
                case "A":
                    int addKey = userInput.nextInt();
                    System.out.print("Enter processor name: ");
                    String addName = userInput.next();
                    System.out.print("Enter clock frequency: ");
                    double addClockFrequency = userInput.nextDouble();
                    System.out.print("Enter cache size: ");
                    int addCacheSize = userInput.nextInt();
                    System.out.print("Enter bus frequency: ");
                    double addBusFrequency = userInput.nextDouble();
                    System.out.print("Enter SPECint result: ");
                    int addSpecInt = userInput.nextInt();
                    System.out.print("Enter SPECfp result: ");
                    int addSpecFp = userInput.nextInt();

                    Processor addedProcessor = new Processor(addKey, addName, addClockFrequency, addCacheSize, addBusFrequency, addSpecInt, addSpecFp);

                    processorTree.insert(addedProcessor);
                    break;
                case "S":
                    processorTree.saveToFile("PROCS.TXT");
                    break;
                case "E":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid command. Please enter a valid command.");
            }
        }

        System.out.println("Exiting the program.");
    }
}
