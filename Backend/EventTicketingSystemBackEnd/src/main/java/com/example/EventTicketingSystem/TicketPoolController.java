package com.example.EventTicketingSystem;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/ticket")
public class TicketPoolController {

    private final TicketPool ticketPool;
    private final TicketPoolService ticketService;
    private boolean systemRunning = false;

    public TicketPoolController(TicketPool ticketPool, TicketPoolService ticketService) {
        this.ticketPool = ticketPool;
        this.ticketService = ticketService;
    }

    @PostMapping("/start")
    public String startSystem() throws IOException {
        try {
            // Attempt to start the system
            ticketService.startSystem();
            System.out.println("System has been started successfully.");
            return "System started successfully.";
        } catch (IOException e) {
            System.err.println("IOException occurred while starting the system: " + e.getMessage());
            return "Failed to start the system due to an input/output error: " + e.getMessage();
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            return "Failed to start the system due to an unexpected error: " + e.getMessage();
        }
    }

    @PostMapping("/stop")
    public String stopSystem() {
        try {
            
            String successMessage = "System stopped successfully";

            System.out.println(successMessage); // Log the success
            return successMessage;
        } catch (IllegalStateException e) {
            // Handle specific exception
            System.err.println("System is not running: " + e.getMessage());
            return "Failed to stop the system: System is not running.";
        } catch (Exception e) {
            // Handle generic exception
            System.err.println("Unexpected error occurred: " + e.getMessage());
            return "Failed to stop the system: " + e.getMessage();
        }
    }
}
