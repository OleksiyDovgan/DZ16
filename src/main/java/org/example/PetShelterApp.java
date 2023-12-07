package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetShelterApp {

    private static final String FILE_PATH = "pets.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final List<Pet> petList = new ArrayList<>();

    public static void main(String[] args) {
        loadPetsFromFile();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addPet(scanner);
                    break;
                case 2:
                    showAllPets();
                    break;
                case 3:
                    takePetFromShelter(scanner);
                    break;
                case 4:
                    savePetsToFile();
                    System.out.println("Exiting the Pet Shelter App. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Pet Shelter Menu:");
        System.out.println("1. Add Pet");
        System.out.println("2. Show All Pets");
        System.out.println("3. Take Pet from Shelter");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addPet(Scanner scanner) {
        System.out.println("Enter pet details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Breed: ");
        String breed = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();

        Pet newPet = new Pet(name, breed, age);
        petList.add(newPet);

        System.out.println("Pet added successfully!");
    }

    private static void showAllPets() {
        if (petList.isEmpty()) {
            System.out.println("No pets in the shelter.");
        } else {
            System.out.println("List of all pets in the shelter:");
            for (Pet pet : petList) {
                System.out.println(pet);
            }
        }
    }

    private static void takePetFromShelter(Scanner scanner) {
        showAllPets();

        if (!petList.isEmpty()) {
            System.out.print("Enter the index of the pet to take from the shelter: ");
            int index = scanner.nextInt();

            if (index >= 0 && index < petList.size()) {
                Pet takenPet = petList.remove(index);
                System.out.println("You took the following pet from the shelter: " + takenPet);
            } else {
                System.out.println("Invalid index. Please try again.");
            }
        }
    }

    private static void loadPetsFromFile() {
        try {
            File file = new File(FILE_PATH);

            if (file.exists()) {
                petList.addAll(objectMapper.readValue(file, new TypeReference<List<Pet>>() {}));
                System.out.println("Pets loaded from file.");
            } else {
                System.out.println("No saved pets found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void savePetsToFile() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), petList);
            System.out.println("Pets saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}