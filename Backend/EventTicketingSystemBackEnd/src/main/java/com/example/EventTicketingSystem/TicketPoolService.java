package com.example.EventTicketingSystem;

import com.example.EventTicketingSystem.ConfigurationForm.ConfigurationForm;
import com.example.EventTicketingSystem.ConfigurationForm.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketPoolService {

    private final TicketPool ticketPool;
    List<Thread> vendorArrayList = new ArrayList<>();
    List<Thread> customerArrayList = new ArrayList<>();
    private boolean systemRunning = true;
    private int totalCustomers = 5;
    private int totalVendors = 3;

    @Autowired
    public TicketPoolService(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public void startSystem() throws IOException {

        // Load configuration details
        ConfigurationForm config = ConfigurationService.loadJson();
        ticketPool.set(config.getMaxTicketCapacity(), config.getTotalTickets());

        // Start vendor threads
        startVendors(config.getTicketReleaseRate(), totalVendors);

        // Start customer threads
        startCustomers(config.getCustomerRetrievalRate(), totalCustomers);

        System.out.println("Ticket sales system started. Processing ticket sales...");
    }


    public void stopSystem(){
        // Stop vendor threads
        stopThreads(vendorArrayList, "Vendor");

        // Stop customer threads
        stopThreads(customerArrayList, "Customer");

        // Clear all thread lists
        clearThreadLists();

    }


    private void startVendors(int ticketReleaseRate, int totalVendors) {
        for (int i = 0; i < totalVendors; i++) {
            String vendorId = "Vendor-" + (i + 1);
            Thread vThread = new Thread(new Vendor(ticketPool, ticketReleaseRate, vendorId));
            vendorArrayList.add(vThread);
            vThread.start();
        }
        System.out.println(totalVendors + " vendor threads started.");
    }

    private void startCustomers(int customerRetrievalRate, int totalCustomers) {
        for (int i = 0; i < totalCustomers; i++) {
            String customerId = "Customer-" + (i + 1);
            Customer customer = new Customer(ticketPool, customerRetrievalRate, customerId);
            Thread cThread = new Thread(customer);
            customerArrayList.add(cThread);
            cThread.start();
        }
        System.out.println(totalCustomers + " customer threads started.");
    }

    private void stopThreads(List<Thread> threadList, String threadType) {
        for (Thread thread : threadList) {
            if (thread.isAlive()) {  // Check if the thread is active
                thread.interrupt();
                System.out.println(threadType + " thread " + thread.getName() + " interrupted.");
            }
        }
    }

    private void clearThreadLists() {
        vendorArrayList.clear();
        customerArrayList.clear();
        System.out.println("All vendor and customer threads cleared.");
    }
}
