package com.example.EventTicketingSystemCLI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        com.example.EventTicketingSystemCLI.ConfigurationForm configuration = new com.example.EventTicketingSystemCLI.ConfigurationForm();

        // CLI logic
        Scanner scanner = new Scanner(System.in);

        // Input and validation
        configuration.setTotalTickets(IntPrompt(scanner, "Enter Total Ticket Count: "));
        configuration.setTicketReleaseRate(IntPrompt(scanner, "Enter Rate of Ticket Release: "));
        configuration.setCustomerRetrievalRate(IntPrompt(scanner, "Enter Rate of Customer Rate: "));
        configuration.setMaxTicketCapacity(IntPrompt(scanner, "Enter the  Capacity of Tickets: "));

        // Save to file
        System.out.println("Configuration Saved...");
        try {
            savingtoJSONFile(configuration, "config.json");
            System.out.println("Successfully saved configuration to config.json file" );
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }

        // Save to text file
        System.out.println("Saving configuration...");
        try {
            savingConfigurationToText(configuration);
            System.out.println("Successfully saved configuration to config.txt file");
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }

        // Load from file and display

        scanner.close();

       TicketPool ticketspool = new com.example.EventTicketingSystemCLI.TicketPool(configuration.getMaxTicketCapacity(), configuration.getTotalTickets());

        for (int i = 0; i < 3; i++) {
            Thread vThread = new Thread(
                    new com.example.EventTicketingSystemCLI.Vendor(ticketspool, configuration.getTicketReleaseRate(),"Vendor-" + (i + 1)));
            vThread.start();

        }

        for (int i = 0; i < 5; i++) {
            Thread cThread = new Thread(
                    new com.example.EventTicketingSystemCLI.Customer(ticketspool, configuration.getCustomerRetrievalRate(),"Customer-" + (i + 1)));
            cThread.start();
        }
    }

    private static int IntPrompt(Scanner scanner, String message) {
        int value;
        while (true) {
            System.out.print(message);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value > 0) {
                    break;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return value;
    }

    // Save configuration to JSON file
    public static void savingtoJSONFile(ConfigurationForm configurationForm, String filePath) throws IOException {
        // Manually construct a JSON string
        String json = "{\n" +
                "  \"TotalTickets\": " + configurationForm.getTotalTickets() + ",\n" +
                "  \"CustomerRetrievalRate\": " + configurationForm.getCustomerRetrievalRate() + ",\n" +
                "  \"TicketReleaseRate\": " + configurationForm.getTicketReleaseRate() + ",\n" +
                "  \"MaxTicketCapacity\": " + configurationForm.getMaxTicketCapacity() + "\n" +
                "}";

        // Write the JSON string to the file
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(json);
        }
    }

    public static void savingConfigurationToText(com.example.EventTicketingSystemCLI.ConfigurationForm config) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("config.txt"))) {
            writer.write("Total Tickets: " + config.getTotalTickets());
            writer.newLine();
            writer.write("Ticket Release Rate: " + config.getTicketReleaseRate());
            writer.newLine();
            writer.write("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
            writer.newLine();
            writer.write(" Ticket Capacity: " + config.getMaxTicketCapacity());
            writer.newLine();
        }
    }
}