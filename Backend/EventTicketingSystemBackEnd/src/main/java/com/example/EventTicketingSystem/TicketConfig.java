package com.example.EventTicketingSystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketConfig {
    @Bean
    public TicketPool ticketPool() {
        // Initialize TicketPool with custom values
        int initialTickets = 100;
        int maxCapacity = 200;
        return new TicketPool(initialTickets, maxCapacity);
    }
}
