package com.example.EventTicketingSystem.ConfigurationForm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ConfigurationService {

    private static final String CONFIG_FILE = "config.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TEXT_FILE_PATH = "config.txt";

    public void saveJson(ConfigurationForm config) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONFIG_FILE), config);
    }

    public static ConfigurationForm loadJson() throws IOException {
        File file = new File(CONFIG_FILE);
        if (file.exists()) {
            return objectMapper.readValue(file, ConfigurationForm.class);
        } else {
            System.out.println("Configuration file not found, creating a new one.");
            return new ConfigurationForm(); // Return a new instance if the file doesn't exist
        }
    }

    public void saveText(ConfigurationForm config) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEXT_FILE_PATH))) {
            writer.write(String.format("Total Tickets: %d", config.getTotalTickets()));
            writer.newLine();
            writer.write(String.format("Ticket Release Rate: %d", config.getTicketReleaseRate()));
            writer.newLine();
            writer.write(String.format("Customer Retrieval Rate: %d", config.getCustomerRetrievalRate()));
            writer.newLine();
            writer.write(String.format("Max Ticket Capacity: %d", config.getMaxTicketCapacity()));
            writer.newLine();
        }
    }
}
